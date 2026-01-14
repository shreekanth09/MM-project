package com.example.workpulse.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.adapters.ViewPagerAdapter
import com.example.workpulse.databinding.ActivityAttendanceLeaveBinding
import com.google.android.material.tabs.TabLayoutMediator

class AttendanceLeaveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAttendanceLeaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceLeaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        // Pass the user role from the intent to the adapter
        val userRole = intent.getStringExtra("user_role")
        val adapter = ViewPagerAdapter(this, userRole)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Attendance"
                1 -> "Leave Requests"
                else -> null
            }
        }.attach()
    }
}
