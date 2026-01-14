package com.example.workpulse.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeaveRequest(
    val employeeEmail: String,
    val leaveType: String,
    val description: String,
    val fromDate: String,
    val toDate: String,
    var status: String // "PENDING", "APPROVED", "REJECTED"
) : Parcelable
