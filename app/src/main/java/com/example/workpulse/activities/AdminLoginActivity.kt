package com.example.workpulse.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityAdminLoginBinding

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            handleAdminLogin()
        }
    }

    private fun handleAdminLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            Toast.makeText(this, "Admin login successful!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, AdminDashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
        }
    }
}