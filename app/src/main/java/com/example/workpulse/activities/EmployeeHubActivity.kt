package com.example.workpulse.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workpulse.adapters.EmployeeAdapter
import com.example.workpulse.databinding.ActivityEmployeeHubBinding
import com.example.workpulse.managers.EmployeeManager
import com.example.workpulse.models.Employee

class EmployeeHubActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeHubBinding
    private lateinit var employeeAdapter: EmployeeAdapter

    private var allEmployees: List<Employee> = emptyList()
    private var filteredEmployees: MutableList<Employee> = mutableListOf()

    private val addEmployeeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // onResume will handle the refresh
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        // Refresh the list every time the user returns to this screen
        loadEmployeesFromManager()
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener { finish() }

        binding.btnAddEmployee.setOnClickListener {
            addEmployeeLauncher.launch(Intent(this, AddEmployeeActivity::class.java))
        }

        binding.chipAll.setOnClickListener { filterEmployees("All") }
        binding.chipMarketing.setOnClickListener { filterEmployees("Marketing") }
        binding.chipDesign.setOnClickListener { filterEmployees("Design") }
        binding.chipEngineering.setOnClickListener { filterEmployees("Engineering") }
    }

    private fun setupRecyclerView() {
        // The adapter now uses the mutable filteredEmployees list and a delete callback
        employeeAdapter = EmployeeAdapter(filteredEmployees) { employee ->
            showDeleteConfirmation(employee)
        }
        binding.rvEmployees.apply {
            layoutManager = LinearLayoutManager(this@EmployeeHubActivity)
            adapter = employeeAdapter
        }
    }

    private fun showDeleteConfirmation(employee: Employee) {
        AlertDialog.Builder(this)
            .setTitle("Delete Employee")
            .setMessage("Are you sure you want to delete ${employee.name}?")
            .setPositiveButton("Delete") { _, _ ->
                EmployeeManager.deleteEmployee(employee.email)
                loadEmployeesFromManager()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun loadEmployeesFromManager() {
        allEmployees = EmployeeManager.getEmployees()
        filterEmployees("All") // Initially, show all employees
    }

    private fun filterEmployees(department: String) {
        filteredEmployees.clear()
        if (department == "All") {
            filteredEmployees.addAll(allEmployees)
        } else {
            filteredEmployees.addAll(allEmployees.filter { it.department == department })
        }
        employeeAdapter.notifyDataSetChanged()
    }
}
