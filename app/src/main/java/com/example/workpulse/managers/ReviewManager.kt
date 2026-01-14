package com.example.workpulse.managers

import android.content.Context
import android.content.SharedPreferences
import com.example.workpulse.models.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ReviewManager {

    private const val PREF_NAME = "ReviewStore"
    private const val KEY_REVIEWS = "review_list"
    private val gson = Gson()
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun addReview(review: Review) {
        val currentReviews = getReviews().toMutableList()
        currentReviews.add(0, review)
        saveReviews(currentReviews)
    }

    fun getReviews(): List<Review> {
        val json = sharedPreferences?.getString(KEY_REVIEWS, null)
        return if (json != null) {
            val type = object : TypeToken<List<Review>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun saveReviews(reviews: List<Review>) {
        val json = gson.toJson(reviews)
        sharedPreferences?.edit()?.putString(KEY_REVIEWS, json)?.apply()
    }

    fun deleteReview(reviewToDelete: Review) {
        val currentReviews = getReviews().toMutableList()
        currentReviews.removeAll { 
            it.employeeName == reviewToDelete.employeeName && 
            it.remarks == reviewToDelete.remarks && 
            it.averageRating == reviewToDelete.averageRating 
        }
        saveReviews(currentReviews)
    }
}
