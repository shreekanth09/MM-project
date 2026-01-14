package com.example.workpulse.activities

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityReviewDetailsBinding
import com.example.workpulse.models.Review

class ReviewDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        val review = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("review", Review::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("review")
        }

        if (review == null) {
            Toast.makeText(this, "Error: Review details not found.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        displayReviewDetails(review)
    }

    private fun displayReviewDetails(review: Review) {
        binding.tvEmployeeName.text = review.employeeName
        binding.tvEmployeeDepartment.text = review.department
        binding.tvOverallGrade.text = "Grade: ${review.grade}"

        binding.rbWorkQuality.rating = review.workQuality
        binding.rbCommunication.rating = review.communication
        binding.rbTimeManagement.rating = review.timeManagement
        binding.rbTeamwork.rating = review.teamwork
        binding.rbDiscipline.rating = review.discipline

        if (review.remarks.isNotEmpty()) {
            binding.tvRemarks.text = review.remarks
        } else {
            binding.tvRemarks.text = "No remarks provided."
        }
    }
}
