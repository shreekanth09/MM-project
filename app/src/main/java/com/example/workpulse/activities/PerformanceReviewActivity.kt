package com.example.workpulse.activities

import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityPerformanceReviewBinding

class PerformanceReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerformanceReviewBinding

    // Variables to store rating values
    private var communicationRating: Float = 0.0f
    private var taskCompletionRating: Float = 0.0f
    private var attendanceRating: Float = 0.0f
    private var workQualityRating: Float = 0.0f
    private var disciplineRating: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerformanceReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Receive Employee Data
        val employeeName = intent.getStringExtra("employeeName") ?: "N/A"
        val employeeId = intent.getStringExtra("employeeId") ?: "N/A"

        // 2. Display Employee Data
        binding.tvEmployeeName.text = employeeName
        binding.tvEmployeeId.text = employeeId

        binding.ivBack.setOnClickListener { finish() }

        // 3. Handle Star Clicks
        setupRatingBarListeners()

        // 4. Handle Submit Button Click
        binding.btnSubmitReview.setOnClickListener {
            if (isAtLeastOneRatingGiven()) {
                // In the future, you would save the ratings to a database here.
                Toast.makeText(this, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
                finish() // Go back to the previous screen
            } else {
                Toast.makeText(this, "Please provide at least one rating.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRatingBarListeners() {
        binding.rbCommunication.onRatingBarChangeListener = 
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                communicationRating = rating
            }

        binding.rbTaskCompletion.onRatingBarChangeListener = 
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                taskCompletionRating = rating
            }

        binding.rbAttendance.onRatingBarChangeListener = 
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                attendanceRating = rating
            }

        binding.rbWorkQuality.onRatingBarChangeListener = 
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                workQualityRating = rating
            }

        binding.rbDiscipline.onRatingBarChangeListener = 
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                disciplineRating = rating
            }
    }

    private fun isAtLeastOneRatingGiven(): Boolean {
        return communicationRating > 0 || 
               taskCompletionRating > 0 || 
               attendanceRating > 0 || 
               workQualityRating > 0 || 
               disciplineRating > 0
    }
}
