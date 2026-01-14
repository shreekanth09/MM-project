package com.example.workpulse.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.R
import com.example.workpulse.databinding.ItemSubmittedReviewBinding
import com.example.workpulse.models.SubmittedReview

class SubmittedReviewAdapter(private val reviews: List<SubmittedReview>) :
    RecyclerView.Adapter<SubmittedReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(private val binding: ItemSubmittedReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: SubmittedReview) {
            binding.tvEmployeeName.text = review.employeeName
            binding.rbAverageRating.rating = review.averageRating
            binding.tvRatingGrade.text = getGradeFromRating(review.averageRating)
            binding.tvRatingGrade.setTextColor(getGradeColor(review.averageRating))
        }

        private fun getGradeFromRating(rating: Float): String {
            return when {
                rating >= 4.5 -> "A+"
                rating >= 4.0 -> "A"
                rating >= 3.5 -> "B+"
                rating >= 3.0 -> "B"
                rating >= 2.5 -> "C+"
                rating >= 2.0 -> "C"
                else -> "D"
            }
        }

        private fun getGradeColor(rating: Float): Int {
            val context = binding.root.context
            return when {
                rating >= 4.0 -> context.getColor(R.color.green_500)
                rating >= 3.0 -> context.getColor(R.color.yellow_500)
                else -> context.getColor(R.color.red_500)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemSubmittedReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount() = reviews.size
}
