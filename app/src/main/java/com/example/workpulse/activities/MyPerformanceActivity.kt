package com.example.workpulse.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityMyPerformanceBinding
import com.example.workpulse.managers.EmployeeSessionManager
import com.example.workpulse.managers.TaskManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MyPerformanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPerformanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPerformanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        // Refresh performance data every time the screen is viewed
        calculateAndDisplayPerformance()
    }

    private fun calculateAndDisplayPerformance() {
        val employeeEmail = EmployeeSessionManager.getEmail()
        if (employeeEmail == null) {
            Toast.makeText(this, "Employee not logged in.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // 1. Fetch ALL tasks and filter manually for the current user
        val allTasks = TaskManager.getTasks()
        val myTasks = allTasks.filter { task ->
            task.assignedEmployeeEmail.equals(employeeEmail, ignoreCase = true)
        }

        // 2. Calculate metrics using explicit lambda parameters
        val assigned = myTasks.count { task -> task.status == "ASSIGNED" }
        val inProgress = myTasks.count { task -> task.status == "IN_PROGRESS" }
        val completed = myTasks.count { task -> task.status == "COMPLETED" }
        val onTime = completed // On Time is the same as Completed

        // Overdue logic: status is not COMPLETED and dueDate is in the past
        val overdue = myTasks.count { task ->
            val isOverdue: Boolean = try {
                val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                val dueDate = sdf.parse(task.dueDate)
                val today = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.time
                dueDate != null && dueDate.before(today)
            } catch (e: Exception) {
                false // Treat invalid date formats as not overdue
            }
            task.status != "COMPLETED" && isOverdue
        }

        // Percentage logic based on specified total task calculation
        val totalTasks = assigned + inProgress + completed + overdue
        val performancePercent = if (totalTasks > 0) {
            (completed * 100) / totalTasks
        } else {
            0
        }

        // 3. Update UI using the correct ViewBinding IDs and remove non-existent ones
        binding.tvTasksAssigned.text = assigned.toString()
        binding.tvTasksCompleted.text = completed.toString()
        binding.tvTasksOnTime.text = onTime.toString()
        binding.tvTasksOverdue.text = overdue.toString()

        // binding.progressBar.progress = performancePercent // REMOVED: This ID is unresolved.
        binding.tvPerformanceScore.text = "$performancePercent%"

        val monthSdf = SimpleDateFormat("MMMM", Locale.getDefault())
        binding.tvPerformanceMonth.text = monthSdf.format(Date())

        binding.tvPerformanceStatus.text = when {
            performancePercent >= 90 -> "Excellent"
            performancePercent >= 75 -> "Good"
            performancePercent >= 50 -> "Average"
            else -> "Needs Improvement"
        }
    }
}