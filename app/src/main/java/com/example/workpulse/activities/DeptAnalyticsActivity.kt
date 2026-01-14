package com.example.workpulse.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.R
import com.example.workpulse.databinding.ActivityDeptAnalyticsBinding
import com.example.workpulse.managers.EmployeeManager
import com.example.workpulse.models.Employee

class DeptAnalyticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeptAnalyticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeptAnalyticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        loadDeptAnalytics()
    }

    private fun loadDeptAnalytics() {
        val allEmployees = EmployeeManager.getEmployees()
        binding.tvTotalEmployees.text = "Total Employees: ${allEmployees.size}"

        if (allEmployees.isEmpty()) return

        val deptGroups = allEmployees.groupBy { it.department }
        val maxEmployeesInADept = deptGroups.values.maxOfOrNull { it.size } ?: 1

        val inflater = LayoutInflater.from(this)

        binding.chartContainer.removeAllViews()
        binding.overviewContainer.removeAllViews()
        binding.performanceGrid.removeAllViews()

        deptGroups.forEach { (deptName, employees) ->
            // 1. Add Bar to Chart
            val barView = inflater.inflate(R.layout.item_vertical_bar, binding.chartContainer, false)
            val barValue = barView.findViewById<TextView>(R.id.tvBarValue)
            val bar = barView.findViewById<View>(R.id.bar)
            val barLabel = barView.findViewById<TextView>(R.id.tvBarLabel)

            barValue.text = employees.size.toString()
            barLabel.text = deptName
            
            // Adjust bar height proportionally
            val params = bar.layoutParams
            params.height = (100 * employees.size / maxEmployeesInADept).coerceAtLeast(10) * resources.displayMetrics.density.toInt()
            bar.layoutParams = params
            
            binding.chartContainer.addView(barView)

            // 2. Add Row to Overview
            val overviewView = inflater.inflate(R.layout.item_dept_overview_row_final, binding.overviewContainer, false)
            val tvDeptName = overviewView.findViewById<TextView>(R.id.tvDeptName)
            val tvEfficiency = overviewView.findViewById<TextView>(R.id.tvEfficiency)
            val tvAttendance = overviewView.findViewById<TextView>(R.id.tvAttendance)
            val tvGoalCompletion = overviewView.findViewById<TextView>(R.id.tvGoalCompletion)

            val avgPerformance = employees.map { it.performanceScore }.average().toInt()
            val avgAttendance = employees.map { it.attendancePercentage }.average().toInt()

            tvDeptName.text = deptName
            tvEfficiency.text = "Efficiency: $avgPerformance%"
            tvAttendance.text = "$avgAttendance%"
            tvGoalCompletion.text = "Goal: ${avgPerformance + 2}%" // Mock goal slightly higher

            binding.overviewContainer.addView(overviewView)

            // 3. Add Card to Performance Grid
            val performanceView = inflater.inflate(R.layout.item_dept_performance_card_final, binding.performanceGrid, false)
            val tvDeptNameSummary = performanceView.findViewById<TextView>(R.id.tvDeptNameSummary)
            val tvEmployeeCountSummary = performanceView.findViewById<TextView>(R.id.tvEmployeeCountSummary)
            val tvPerformanceRating = performanceView.findViewById<TextView>(R.id.tvPerformanceRating)

            tvDeptNameSummary.text = deptName
            tvEmployeeCountSummary.text = "Employees: ${employees.size}"
            
            val stars = when {
                avgPerformance >= 90 -> "★★★★★"
                avgPerformance >= 80 -> "★★★★☆"
                avgPerformance >= 70 -> "★★★☆☆"
                avgPerformance >= 60 -> "★★☆☆☆"
                else -> "★☆☆☆☆"
            }
            tvPerformanceRating.text = stars

            binding.performanceGrid.addView(performanceView)
        }
    }
}
