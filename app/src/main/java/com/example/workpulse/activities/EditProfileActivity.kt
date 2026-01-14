package com.example.workpulse.activities

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityEditProfileBinding
import com.example.workpulse.managers.EmployeeSessionManager

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadProfileData()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSaveChanges.setOnClickListener {
            if (validateInput()) {
                saveProfileData()
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK) // Signal to MyProfileActivity to refresh
                finish()
            }
        }
    }

    private fun loadProfileData() {
        val prefs = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val defaultEmail = EmployeeSessionManager.getEmail() ?: "user@example.com"
        
        binding.etFullName.setText(prefs.getString("fullName", "Your Name"))
        binding.etPhoneNumber.setText(prefs.getString("phoneNumber", "Your Phone"))
        binding.etEmployeeId.setText(prefs.getString("employeeId", "EMP-001"))
        binding.etEmail.setText(prefs.getString("email", defaultEmail))
        binding.etDepartment.setText(prefs.getString("department", "Engineering"))
        binding.etDesignation.setText(prefs.getString("designation", "Android Developer"))
    }

    private fun validateInput(): Boolean {
        if (binding.etFullName.text.toString().trim().isEmpty()) {
            binding.etFullName.error = "Full Name cannot be empty"
            return false
        }
        if (binding.etEmail.text.toString().trim().isEmpty()) {
            binding.etEmail.error = "Email cannot be empty"
            return false
        }
        return true
    }

    private fun saveProfileData() {
        val prefs = getSharedPreferences("UserProfile", Context.MODE_PRIVATE).edit()
        prefs.putString("fullName", binding.etFullName.text.toString().trim())
        prefs.putString("phoneNumber", binding.etPhoneNumber.text.toString().trim())
        prefs.putString("employeeId", binding.etEmployeeId.text.toString().trim())
        prefs.putString("email", binding.etEmail.text.toString().trim())
        prefs.putString("department", binding.etDepartment.text.toString().trim())
        prefs.putString("designation", binding.etDesignation.text.toString().trim())
        prefs.apply()
    }
}
