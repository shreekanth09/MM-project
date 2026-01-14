package com.example.workpulse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.databinding.ItemReviewBinding
import com.example.workpulse.models.Review

class ReviewAdapter(
    private val reviews: List<Review>,
    private val onReviewSelected: (Review) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount() = reviews.size

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.tvEmployeeName.text = review.employeeName
            binding.tvReviewGrade.text = "Grade: ${review.grade}"
            binding.rbReviewScore.rating = review.averageRating

            itemView.setOnClickListener {
                onReviewSelected(review)
            }
        }
    }
}
