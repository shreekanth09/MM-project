package com.example.workpulse.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workpulse.activities.ApplyLeaveActivity
import com.example.workpulse.adapters.LeaveRequestAdapter
import com.example.workpulse.databinding.FragmentLeaveRequestsBinding
import com.example.workpulse.managers.EmployeeSessionManager
import com.example.workpulse.managers.LeaveManager
import com.example.workpulse.models.LeaveRequest

class LeaveRequestsFragment : Fragment() {

    private var _binding: FragmentLeaveRequestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var leaveRequestAdapter: LeaveRequestAdapter
    private var userRole: String? = null

    private val applyLeaveLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // onResume will handle the list refresh.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaveRequestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRole = requireActivity().intent.getStringExtra("user_role")
        setupRecyclerView()

        if (userRole == "ADMIN") {
            binding.fabApplyLeave.visibility = View.GONE
        } else {
            binding.fabApplyLeave.visibility = View.VISIBLE
            binding.fabApplyLeave.setOnClickListener {
                val intent = Intent(requireContext(), ApplyLeaveActivity::class.java)
                applyLeaveLauncher.launch(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadLeaveRequestsFromManager()
    }

    private fun setupRecyclerView() {
        leaveRequestAdapter = LeaveRequestAdapter(
            emptyList(), 
            userRole,
            onDeleteClick = { leaveRequest ->
                showDeleteConfirmation(leaveRequest)
            },
            onAction = { leaveRequest, newStatus ->
                LeaveManager.updateLeaveStatus(leaveRequest, newStatus)
                loadLeaveRequestsFromManager()
                Toast.makeText(requireContext(), "Status updated to $newStatus", Toast.LENGTH_SHORT).show()
            }
        )
        binding.rvLeaveRequests.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = leaveRequestAdapter
        }
    }

    private fun showDeleteConfirmation(leaveRequest: LeaveRequest) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Leave Request")
            .setMessage("Are you sure you want to delete this request?")
            .setPositiveButton("Delete") { _, _ ->
                LeaveManager.deleteLeaveRequest(leaveRequest)
                loadLeaveRequestsFromManager()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun loadLeaveRequestsFromManager() {
        val leaveRequests = if (userRole == "ADMIN") {
            LeaveManager.getAllLeaveRequests()
        } else {
            val employeeEmail = EmployeeSessionManager.getEmail()
            if (employeeEmail != null) {
                LeaveManager.getLeaveRequestsForEmployee(employeeEmail)
            } else {
                emptyList()
            }
        }
        updateLeaveListUI(leaveRequests)
    }
	
    private fun updateLeaveListUI(leaveRequests: List<LeaveRequest>) {
        leaveRequestAdapter.updateLeaveRequests(leaveRequests.sortedByDescending { it.status == "PENDING" })

        if (leaveRequests.isEmpty()) {
            binding.rvLeaveRequests.visibility = View.GONE
            binding.tvNoLeaveRequests.visibility = View.VISIBLE
        } else {
            binding.rvLeaveRequests.visibility = View.VISIBLE
            binding.tvNoLeaveRequests.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
