package com.example.workpulse.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workpulse.adapters.AnnouncementAdapter
import com.example.workpulse.databinding.ActivityNotificationsBinding
import com.example.workpulse.managers.AnnouncementManager

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvAnnouncements.layoutManager = LinearLayoutManager(this)
        loadAnnouncements()
    }

    private fun loadAnnouncements() {
        val announcements = AnnouncementManager.getAnnouncements()
        // Pass an empty lambda for onDeleteClick since users shouldn't delete from here
        binding.rvAnnouncements.adapter = AnnouncementAdapter(announcements) {
            // No action needed for notifications view
        }

        if (announcements.isEmpty()) {
            Toast.makeText(this, "No announcements found.", Toast.LENGTH_SHORT).show()
        }
    }
}
