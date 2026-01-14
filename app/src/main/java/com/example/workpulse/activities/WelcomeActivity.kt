package com.example.workpulse.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdminLogin.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }

        binding.btnEmployeeLogin.setOnClickListener {
            startActivity(Intent(this, EmployeeLoginActivity::class.java))
        }
    }
}
