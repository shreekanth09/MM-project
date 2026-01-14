package com.example.workpulse.models

data class Announcement(
    val title: String,
    val message: String,
    val date: String,
    val category: String = "General"
)
