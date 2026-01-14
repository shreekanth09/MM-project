package com.example.workpulse.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.databinding.ItemLeaveRequestBinding
import com.example.workpulse.models.LeaveRequest
import java.text.SimpleDateFormat
import java.util.*

class LeaveRequestAdapter(
    private var leaveRequests: List<LeaveRequest>,
    private val userRole: String?,
    private val onDeleteClick: (LeaveRequest) -> Unit,
    private val onAction: (leaveRequest: LeaveRequest, newStatus: String) -> Unit
) : RecyclerView.Adapter<LeaveRequestAdapter.LeaveRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaveRequestViewHolder {
        val binding = ItemLeaveRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaveRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaveRequestViewHolder, position: Int) {
        holder.bind(leaveRequests[position])
    }

    override fun getItemCount() = leaveRequests.size

    fun updateLeaveRequests(newLeaveRequests: List<LeaveRequest>?) {
        if (newLeaveRequests != null) {
            this.leaveRequests = newLeaveRequests
            notifyDataSetChanged()
        }
    }

    inner class LeaveRequestViewHolder(private val binding: ItemLeaveRequestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(leaveRequest: LeaveRequest) {
            binding.tvLeaveType.text = leaveRequest.leaveType
            binding.tvLeaveDateRange.text = "${leaveRequest.fromDate} - ${leaveRequest.toDate}"
            binding.tvLeaveStatus.text = leaveRequest.status

            if (userRole == "ADMIN") {
                binding.tvEmployeeName.visibility = View.VISIBLE
                binding.tvEmployeeName.text = leaveRequest.employeeEmail
                binding.tvLeaveDescription.visibility = View.VISIBLE
                binding.tvLeaveDescription.text = leaveRequest.description

                if (leaveRequest.status == "PENDING") {
                    binding.adminActionsLayout.visibility = View.VISIBLE
                    binding.btnApprove.setOnClickListener { onAction(leaveRequest, "APPROVED") }
                    binding.btnReject.setOnClickListener { onAction(leaveRequest, "REJECTED") }
                } else {
                    binding.adminActionsLayout.visibility = View.GONE
                }
                binding.ivDeleteLeave.visibility = View.VISIBLE
            } else {
                binding.tvEmployeeName.visibility = View.GONE
                binding.tvLeaveDescription.visibility = View.GONE
                binding.adminActionsLayout.visibility = View.GONE
                binding.ivDeleteLeave.visibility = View.GONE
            }

            binding.ivDeleteLeave.setOnClickListener {
                onDeleteClick(leaveRequest)
            }

            val statusColor = when (leaveRequest.status.uppercase()) {
                "APPROVED" -> Color.GREEN
                "REJECTED" -> Color.RED
                else -> Color.GRAY
            }
            binding.tvLeaveStatus.setTextColor(statusColor)
        }
    }
}
