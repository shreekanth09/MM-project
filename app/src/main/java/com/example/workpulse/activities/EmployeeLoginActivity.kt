package com.example.workpulse.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityEmployeeLoginBinding
import com.example.workpulse.managers.EmployeeSessionManager

class EmployeeLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            handleEmployeeLogin()
        }

        binding.tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Password reset feature coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEmployeeLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Correct credentials: Save session and navigate
            EmployeeSessionManager.saveSession(email)

            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, EmployeeDashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } else {
            // Incorrect credentials: Show a Toast message
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
        }
    }
}