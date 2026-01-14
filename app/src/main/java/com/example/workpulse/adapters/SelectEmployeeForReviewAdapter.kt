package com.example.workpulse.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.activities.PerformanceReviewActivity
import com.example.workpulse.databinding.ItemEmployeeForReviewBinding
import com.example.workpulse.models.Employee

class SelectEmployeeForReviewAdapter(private val employees: List<Employee>) :
    RecyclerView.Adapter<SelectEmployeeForReviewAdapter.EmployeeViewHolder>() {

    inner class EmployeeViewHolder(private val binding: ItemEmployeeForReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(employee: Employee) {
            binding.tvEmployeeName.text = employee.name
            binding.tvEmployeeId.text = employee.id
            binding.tvDepartment.text = employee.department

            binding.btnReview.setOnClickListener {
                val context = it.context
                val intent = Intent(context, PerformanceReviewActivity::class.java).apply {
                    putExtra("employeeId", employee.id)
                    putExtra("employeeName", employee.name)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ItemEmployeeForReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employees[position])
    }

    override fun getItemCount() = employees.size
}
