package com.example.workpulse.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.R
import com.example.workpulse.databinding.ActivityWriteReviewBinding
import com.example.workpulse.managers.ReviewManager
import com.example.workpulse.models.Review

class WriteReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteReviewBinding
    private var employeeName: String? = null
    private var employeeEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeName = intent.getStringExtra("employeeName")
        employeeEmail = intent.getStringExtra("employeeEmail")

        if (employeeName == null || employeeEmail == null) {
            Toast.makeText(this, "Error: Employee details are missing.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        binding.tvEmployeeName.text = "Review for: $employeeName"

        setupSpinner()
        setupClickListeners()
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.review_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerReviewType.adapter = adapter
        }
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener { finish() }

        binding.btnSubmitReview.setOnClickListener {
            submitReview()
        }
    }

    private fun submitReview() {
        val rating = binding.ratingBar.rating
        val reviewType = binding.spinnerReviewType.selectedItem.toString()
        val comments = binding.etComments.text.toString()

        if (rating == 0f) {
            Toast.makeText(this, "Please provide a star rating.", Toast.LENGTH_SHORT).show()
            return
        }

        if (comments.trim().isEmpty()) {
            Toast.makeText(this, "Please provide some comments.", Toast.LENGTH_SHORT).show()
            return
        }

        val grade = when {
            rating >= 4.5 -> "A+"
            rating >= 4.0 -> "A"
            rating >= 3.5 -> "B+"
            rating >= 3.0 -> "B"
            else -> "C"
        }

        val review = Review(
            employeeName = employeeName!!,
            department = reviewType, // Using review type as department for now
            workQuality = rating, // Simplified model
            communication = rating,
            timeManagement = rating,
            teamwork = rating,
            discipline = rating,
            remarks = comments,
            averageRating = rating,
            grade = grade
        )

        ReviewManager.addReview(review)
        Toast.makeText(this, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
