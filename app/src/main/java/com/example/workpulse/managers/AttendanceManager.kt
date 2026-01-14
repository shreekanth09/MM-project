package com.example.workpulse.managers

import android.content.Context
import com.example.workpulse.models.AttendanceRecord
import com.example.workpulse.models.Employee
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object AttendanceManager {

    fun getAttendanceRecords(context: Context): List<AttendanceRecord> {
        EmployeeManager.init(context)
        val employees = EmployeeManager.getEmployees()
        val attendanceRecords = mutableListOf<AttendanceRecord>()
        val todayDate = getCurrentDate()

        for (employee in employees) {
            val sharedPreferences = context.getSharedPreferences("AttendancePrefs_${employee.email}", Context.MODE_PRIVATE)
            val checkInTime = sharedPreferences.getString("${todayDate}_CHECK_IN", null)

            val status = if (checkInTime != null) "Present" else "Absent"
            attendanceRecords.add(AttendanceRecord(employee.name, status))
        }

        return attendanceRecords
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return sdf.format(Date())
    }
}