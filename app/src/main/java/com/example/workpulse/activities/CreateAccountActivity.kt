package com.example.workpulse.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener {
            if (validateInputs()) {
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                // You can add navigation to another screen here if needed
                finish()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (fullName.isEmpty()) {
            binding.etFullName.error = "Full Name is required"
            binding.etFullName.requestFocus()
            return false
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            binding.etEmail.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            binding.etPassword.requestFocus()
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.error = "Confirm Password is required"
            binding.etConfirmPassword.requestFocus()
            return false
        }

        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Passwords do not match"
            binding.etConfirmPassword.requestFocus()
            return false
        }

        if (binding.rgRoles.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
