package com.example.workpulse.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workpulse.adapters.AnnouncementAdapter
import com.example.workpulse.databinding.ActivityAlertsNoticesBinding
import com.example.workpulse.managers.AnnouncementManager
import com.example.workpulse.models.Announcement

class AlertsNoticesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlertsNoticesBinding

    private val createAnnouncementLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // No action is needed here as onResume will be called upon returning.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertsNoticesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        loadAnnouncements()
    }

    private fun setupRecyclerView() {
        binding.rvAlerts.layoutManager = LinearLayoutManager(this)
    }

    private fun loadAnnouncements() {
        val announcements = AnnouncementManager.getAnnouncements()
        binding.rvAlerts.adapter = AnnouncementAdapter(announcements) { announcement ->
            showDeleteConfirmation(announcement)
        }
        updateEmptyState(announcements.isEmpty())
    }

    private fun showDeleteConfirmation(announcement: Announcement) {
        AlertDialog.Builder(this)
            .setTitle("Delete Announcement")
            .setMessage("Are you sure you want to delete this announcement?")
            .setPositiveButton("Delete") { _, _ ->
                AnnouncementManager.deleteAnnouncement(announcement)
                loadAnnouncements()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener { finish() }

        binding.fabCreateAnnouncement.setOnClickListener {
            createAnnouncementLauncher.launch(Intent(this, CreateAnnouncementActivity::class.java))
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvAlerts.visibility = View.GONE
            binding.tvEmptyState.visibility = View.VISIBLE
        } else {
            binding.rvAlerts.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.GONE
        }
    }
}
