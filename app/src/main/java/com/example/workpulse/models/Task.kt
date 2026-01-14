package com.example.workpulse.models

data class Task(
    val title: String,
    val description: String,
    val assignedEmployeeName: String,
    val assignedEmployeeEmail: String,
    val dueDate: String,
    val priority: String,
    var status: String = "ASSIGNED" // Corrected: Use uppercase for consistency
)
