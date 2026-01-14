package com.example.workpulse.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityMyProfileBinding
import com.example.workpulse.managers.EmployeeManager
import com.example.workpulse.managers.EmployeeSessionManager

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding
    private var employeeEmail: String? = null

    private val editProfileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Refresh data when returning from EditProfileActivity
            loadProfileData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeEmail = EmployeeSessionManager.getEmail()

        if (employeeEmail == null) {
            Toast.makeText(this, "Error: Employee not logged in.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        loadProfileData()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnEditProfile.setOnClickListener {
            editProfileLauncher.launch(Intent(this, EditProfileActivity::class.java))
        }
    }

    private fun loadProfileData() {
        // Fetch the employee's profile from EmployeeManager using the logged-in email
        val employee = EmployeeManager.getEmployees().find { it.email.equals(employeeEmail, ignoreCase = true) }

        if (employee != null) {
            binding.tvFullName.text = employee.name
            binding.tvEmailValue.text = employee.email
            binding.tvPhoneValue.text = employee.phone
            binding.tvDepartmentValue.text = employee.department
            binding.tvDesignationValue.text = employee.designation
            binding.tvEmployeeIdValue.text = employee.id
            
            // Set initials for the avatar if applicable (e.g., Shreekanth -> S)
            val initials = if (employee.name.isNotEmpty()) {
                val names = employee.name.trim().split(" ")
                if (names.size >= 2) {
                    "${names[0][0]}${names[names.size - 1][0]}".uppercase()
                } else {
                    "${names[0][0]}".uppercase()
                }
            } else "?"
            
            binding.tvUserInitials.text = initials
        } else {
            // Fallback for cases where employee data is missing or it's the admin
            binding.tvFullName.text = "Admin"
            binding.tvEmailValue.text = employeeEmail
            binding.tvUserInitials.text = "AD"
        }
    }
}
