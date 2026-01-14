package com.example.workpulse.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityEmployeeDashboardBinding
import com.example.workpulse.managers.EmployeeSessionManager
import com.example.workpulse.managers.TaskManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EmployeeDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvWelcome.text = "Welcome back!"

        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        updateDashboardCards()
    }

    private fun updateDashboardCards() {
        val employeeEmail = EmployeeSessionManager.getEmail()
        if (employeeEmail == null) {
            Toast.makeText(this, "Error: Employee not logged in.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val allTasks = TaskManager.getTasks()
        val myTasks = allTasks.filter { task ->
            task.assignedEmployeeEmail.equals(employeeEmail, ignoreCase = true)
        }

        val completedTasks = myTasks.count { task -> task.status == "COMPLETED" }
        val totalTasks = myTasks.size
        val performancePercent = if (totalTasks > 0) (completedTasks * 100) / totalTasks else 0

        val attendancePrefs = getSharedPreferences("AttendancePrefs_${employeeEmail}", Context.MODE_PRIVATE)
        val presentDays = attendancePrefs.getInt("presentDays", 0)
        val totalWorkingDays = 25
        val attendancePercent = if (totalWorkingDays > 0) (presentDays * 100) / totalWorkingDays else 0

        val pendingActions = myTasks.count { task ->
            task.status == "ASSIGNED" || task.status == "IN_PROGRESS"
        }

        val todaySdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        val todayDateStr = todaySdf.format(Date())
        val todaysTasks = myTasks.count { task -> task.dueDate == todayDateStr }

        // Updated UI access for Glassmorphism design
        binding.cardPerformance.tvMetricValue.text = "$performancePercent%"
        binding.cardPerformance.tvMetricLabel.text = "Performance"

        binding.cardAttendance.tvMetricValue.text = "$attendancePercent%"
        binding.cardAttendance.tvMetricLabel.text = "Attendance"

        binding.cardPending.tvMetricValue.text = pendingActions.toString()
        binding.cardPending.tvMetricLabel.text = "Pending"

        binding.cardTasks.tvMetricValue.text = todaysTasks.toString()
        binding.cardTasks.tvMetricLabel.text = "Today's Tasks"
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

        binding.cardMyPerformance.setOnClickListener {
            startActivity(Intent(this, MyPerformanceActivity::class.java))
        }

        binding.cardMyTasks.setOnClickListener {
            startActivity(Intent(this, MyTasksActivity::class.java))
        }

        binding.cardAttendanceLeave.setOnClickListener {
            startActivity(Intent(this, AttendanceLeaveActivity::class.java))
        }

        binding.cardMyProfile.setOnClickListener {
            startActivity(Intent(this, MyProfileActivity::class.java))
        }

        binding.cardNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        binding.cardReports.setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                EmployeeSessionManager.clearSession()
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
