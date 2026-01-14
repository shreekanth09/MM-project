package com.example.workpulse.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.R
import com.example.workpulse.databinding.ActivityCreateAnnouncementBinding
import com.example.workpulse.managers.AnnouncementManager
import com.example.workpulse.models.Announcement
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateAnnouncementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAnnouncementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAnnouncementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup the category dropdown
        val categories = listOf("General", "Info", "Urgent")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter

        binding.ivBack.setOnClickListener { finish() }

        binding.btnPublish.setOnClickListener {
            if (validateInput()) {
                createAndSaveAnnouncement()
                Toast.makeText(this, "Announcement Published!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun validateInput(): Boolean {
        if (binding.etAnnouncementTitle.text.isNullOrEmpty() || binding.etAnnouncementContent.text.isNullOrEmpty()) {
            Toast.makeText(this, "Title and message cannot be empty.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.spinnerCategory.selectedItem == null) {
            Toast.makeText(this, "Please select a category.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun createAndSaveAnnouncement() {
        val title = binding.etAnnouncementTitle.text.toString().trim()
        val message = binding.etAnnouncementContent.text.toString().trim()
        val category = binding.spinnerCategory.selectedItem.toString()
        val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

        val newAnnouncement = Announcement(title, message, currentDate, category)
        AnnouncementManager.addAnnouncement(newAnnouncement)
    }
}
