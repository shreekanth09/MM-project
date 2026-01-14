package com.example.workpulse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.databinding.ItemEmployeeBinding
import com.example.workpulse.models.Employee

class EmployeeAdapter(
    private var employees: MutableList<Employee>,
    private val onDeleteClick: (Employee) -> Unit
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    inner class EmployeeViewHolder(private val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee) {
            binding.tvEmployeeName.text = employee.name
            binding.tvEmail.text = employee.email
            binding.tvDepartment.text = employee.department
            
            binding.ivDelete.setOnClickListener {
                onDeleteClick(employee)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ItemEmployeeBinding.inflate(
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

    fun updateEmployees(newEmployees: List<Employee>) {
        employees.clear()
        employees.addAll(newEmployees)
        notifyDataSetChanged()
    }
}
