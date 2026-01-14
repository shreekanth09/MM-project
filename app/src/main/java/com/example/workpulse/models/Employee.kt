package com.example.workpulse.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val department: String,
    val designation: String,
    val skills: List<String>,
    val performanceScore: Int = 0,
    val attendancePercentage: Int = 0
) : Parcelable
