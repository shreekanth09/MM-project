package com.example.workpulse.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivitySubmitReviewBinding
import com.example.workpulse.managers.ReviewManager
import com.example.workpulse.models.Review

class SubmitReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubmitReviewBinding
    private var employeeName: String? = null
    private var employeeDepartment: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubmitReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeName = intent.getStringExtra("employee_name")
        employeeDepartment = intent.getStringExtra("employee_department")

        if (employeeName == null) {
            Toast.makeText(this, "Error: Employee details not found.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        binding.tvEmployeeName.text = employeeName
        binding.tvEmployeeDepartment.text = employeeDepartment ?: "N/A"

        binding.ivBack.setOnClickListener { finish() }
        
        // CORRECTED: The navigation is ONLY handled by the submit button click.
        binding.btnSubmitReview.setOnClickListener { 
            submitReviewAndNavigate()
        }
    }

    private fun submitReviewAndNavigate() {
        val workQuality = binding.rbWorkQuality.rating
        val communication = binding.rbCommunication.rating
        val timeManagement = binding.rbTimeManagement.rating
        val teamwork = binding.rbTeamwork.rating
        val discipline = binding.rbDiscipline.rating

        if (workQuality == 0f || communication == 0f || timeManagement == 0f || teamwork == 0f || discipline == 0f) {
            Toast.makeText(this, "Please provide a rating for all categories.", Toast.LENGTH_SHORT).show()
            return
        }

        val averageRating = (workQuality + communication + timeManagement + teamwork + discipline) / 5

        val grade = when {
            averageRating >= 4.5 -> "A+"
            averageRating >= 4.0 -> "A"
            averageRating >= 3.5 -> "B+"
            averageRating >= 3.0 -> "B"
            else -> "C"
        }

        val review = Review(
            employeeName = employeeName!!,
            department = employeeDepartment ?: "N/A",
            workQuality = workQuality,
            communication = communication,
            timeManagement = timeManagement,
            teamwork = teamwork,
            discipline = discipline,
            remarks = binding.etRemarks.text.toString(),
            averageRating = averageRating,
            grade = grade
        )

        ReviewManager.addReview(review)
        Toast.makeText(this, "Review submitted successfully!", Toast.LENGTH_SHORT).show()

        // ONLY navigate after submission is complete.
        val intent = Intent(this, SubmittedReviewsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
