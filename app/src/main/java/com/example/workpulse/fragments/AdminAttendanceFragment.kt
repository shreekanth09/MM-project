package com.example.workpulse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workpulse.adapters.AttendanceAdapter
import com.example.workpulse.databinding.FragmentAdminAttendanceBinding
import com.example.workpulse.managers.AttendanceManager

class AdminAttendanceFragment : Fragment() {

    private var _binding: FragmentAdminAttendanceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminAttendanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }
    
    override fun onResume() {
        super.onResume()
        // Refresh data every time the fragment is viewed
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // For Admins, get all attendance records without filtering by employee
        val allRecords = AttendanceManager.getAttendanceRecords(requireContext())
        binding.rvAdminAttendance.adapter = AttendanceAdapter(allRecords)
        binding.rvAdminAttendance.layoutManager = LinearLayoutManager(requireContext())
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
