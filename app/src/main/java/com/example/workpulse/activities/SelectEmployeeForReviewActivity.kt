package com.example.workpulse.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workpulse.adapters.EmployeeSelectionAdapter
import com.example.workpulse.databinding.ActivitySelectEmployeeForReviewBinding
import com.example.workpulse.managers.EmployeeManager
import com.example.workpulse.models.Employee

class SelectEmployeeForReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectEmployeeForReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectEmployeeForReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        setupEmployeeList()
    }

    private fun setupEmployeeList() {
        val employees = EmployeeManager.getEmployees()

        if (employees.isEmpty()) {
            binding.rvEmployees.visibility = View.GONE
            binding.tvNoEmployees.visibility = View.VISIBLE
        } else {
            binding.rvEmployees.visibility = View.VISIBLE
            binding.tvNoEmployees.visibility = View.GONE

            val adapter = EmployeeSelectionAdapter(employees) { selectedEmployee ->
                // CORRECTED: Navigate to WriteReviewActivity and pass employee details.
                val intent = Intent(this, WriteReviewActivity::class.java).apply {
                    putExtra("employeeName", selectedEmployee.name)
                    putExtra("employeeEmail", selectedEmployee.email)
                }
                startActivity(intent)
            }
            binding.rvEmployees.layoutManager = LinearLayoutManager(this)
            binding.rvEmployees.adapter = adapter
        }
    }
}
