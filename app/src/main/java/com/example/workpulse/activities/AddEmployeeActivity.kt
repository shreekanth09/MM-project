package com.example.workpulse.activities

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityAddEmployeeBinding
import com.example.workpulse.managers.EmployeeManager
import com.example.workpulse.models.Employee

class AddEmployeeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        binding.btnSave.setOnClickListener {
            if (validateInputs()) {
                addEmployeeToStore()
                Toast.makeText(this, "Employee added successfully!", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (binding.etName.text.isNullOrEmpty() ||
            binding.etEmployeeId.text.isNullOrEmpty() ||
            binding.etEmail.text.isNullOrEmpty()
        ) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun addEmployeeToStore() {
        val newEmployee = Employee(
            id = binding.etEmployeeId.text.toString().trim(),
            name = binding.etName.text.toString().trim(),
            email = binding.etEmail.text.toString().trim(),
            phone = binding.etPhone.text.toString().trim(),
            department = binding.etDepartment.text.toString().trim(),
            designation = binding.etDesignation.text.toString().trim(),
            skills = binding.etSkills.text.toString().split(",").map { it.trim() }
        )
        // Save the new employee using the EmployeeManager
        EmployeeManager.addEmployee(newEmployee)
    }
}
