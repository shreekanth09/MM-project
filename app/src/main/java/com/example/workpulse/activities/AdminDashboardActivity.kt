package com.example.workpulse.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityAdminDashboardBinding
import com.example.workpulse.managers.AttendanceManager
import com.example.workpulse.managers.EmployeeManager
import com.example.workpulse.managers.TaskManager
import com.example.workpulse.managers.ReviewManager
import com.example.workpulse.managers.AnnouncementManager
import com.example.workpulse.managers.LeaveManager

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize all managers to ensure data is loaded from storage
        EmployeeManager.init(applicationContext)
        TaskManager.init(applicationContext)
        ReviewManager.init(applicationContext)
        AnnouncementManager.init(applicationContext)
        LeaveManager.init(applicationContext)

        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        updateDashboardCards()
    }

    private fun updateDashboardCards() {
        val totalEmployees = EmployeeManager.getEmployees().size
        val pendingLeave = LeaveManager.getAllLeaveRequests().count { it.status == "PENDING" }

        // Update the metric values on the dashboard based on the new design
        binding.tvTotalEmployeesValue.text = totalEmployees.toString()
        binding.tvPendingLeaveValue.text = pendingLeave.toString()
    }

    private fun setupClickListeners() {
        binding.ivLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        binding.ivNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this, MyProfileActivity::class.java))
        }

        binding.cardEmployeeHub.setOnClickListener {
            startActivity(Intent(this, EmployeeHubActivity::class.java))
        }

        binding.btnAddEmployee.setOnClickListener {
            startActivity(Intent(this, AddEmployeeActivity::class.java))
        }

        binding.cardPerformanceReview.setOnClickListener {
            startActivity(Intent(this, SubmittedReviewsActivity::class.java))
        }

        binding.cardWorkloadTasks.setOnClickListener {
            startActivity(Intent(this, WorkloadTasksActivity::class.java))
        }

        binding.cardAttendanceCenter.setOnClickListener {
            val intent = Intent(this, AttendanceCenterActivity::class.java)
            intent.putExtra("user_role", "ADMIN")
            startActivity(intent)
        }

        binding.cardDeptAnalytics.setOnClickListener {
            startActivity(Intent(this, DeptAnalyticsActivity::class.java))
        }

        binding.cardAlertsNotices.setOnClickListener {
            startActivity(Intent(this, AlertsNoticesActivity::class.java))
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE).edit()
                prefs.putBoolean("isLoggedIn", false)
                prefs.apply()

                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
