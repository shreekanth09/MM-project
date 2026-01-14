package com.example.workpulse.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val employeeName: String,
    val department: String,
    val workQuality: Float,
    val communication: Float,
    val timeManagement: Float,
    val teamwork: Float,
    val discipline: Float,
    val remarks: String,
    val averageRating: Float,
    val grade: String
) : Parcelable
