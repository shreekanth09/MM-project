package com.example.workpulse.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workpulse.adapters.ReviewAdapter
import com.example.workpulse.databinding.ActivitySubmittedReviewsBinding
import com.example.workpulse.managers.ReviewManager

class SubmittedReviewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubmittedReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubmittedReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        binding.fabCreateReview.setOnClickListener {
            startActivity(Intent(this, SelectEmployeeForReviewActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // CORRECTED: Always refresh the list when the screen becomes visible.
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val reviews = ReviewManager.getReviews()
        val adapter = ReviewAdapter(reviews) { selectedReview ->
            // Navigate to the read-only details screen
            val intent = Intent(this, ReviewDetailsActivity::class.java)
            intent.putExtra("review", selectedReview)
            startActivity(intent)
        }
        binding.rvSubmittedReviews.layoutManager = LinearLayoutManager(this)
        binding.rvSubmittedReviews.adapter = adapter
    }
}
