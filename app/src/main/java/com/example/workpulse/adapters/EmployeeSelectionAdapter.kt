package com.example.workpulse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.databinding.ItemEmployeeSelectBinding
import com.example.workpulse.models.Employee

class EmployeeSelectionAdapter(
    private val employees: List<Employee>,
    private val onEmployeeSelected: (Employee) -> Unit // Lambda to handle the click
) : RecyclerView.Adapter<EmployeeSelectionAdapter.EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ItemEmployeeSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employees[position])
    }

    override fun getItemCount() = employees.size

    inner class EmployeeViewHolder(private val binding: ItemEmployeeSelectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee) {
            binding.tvEmployeeName.text = employee.name
            binding.tvEmployeeEmail.text = employee.email
            binding.tvEmployeeDepartment.text = employee.department

            // Set the click listener on the item view
            itemView.setOnClickListener {
                onEmployeeSelected(employee)
            }
        }
    }
}
