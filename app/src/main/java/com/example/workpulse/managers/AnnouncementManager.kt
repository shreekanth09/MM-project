package com.example.workpulse.managers

import android.content.Context
import android.content.SharedPreferences
import com.example.workpulse.models.Announcement
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AnnouncementManager {

    private const val PREF_NAME = "AnnouncementStore"
    private const val KEY_ANNOUNCEMENTS = "announcement_list"
    private val gson = Gson()
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun getAnnouncements(): List<Announcement> {
        val json = sharedPreferences?.getString(KEY_ANNOUNCEMENTS, null)
        return if (json != null) {
            val type = object : TypeToken<List<Announcement>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addAnnouncement(announcement: Announcement) {
        val currentAnnouncements = getAnnouncements().toMutableList()
        currentAnnouncements.add(0, announcement)
        saveAnnouncements(currentAnnouncements)
    }

    fun deleteAnnouncement(announcement: Announcement) {
        val currentAnnouncements = getAnnouncements().toMutableList()
        currentAnnouncements.removeAll { it.title == announcement.title && it.date == announcement.date && it.message == announcement.message }
        saveAnnouncements(currentAnnouncements)
    }

    private fun saveAnnouncements(announcements: List<Announcement>) {
        val json = gson.toJson(announcements)
        sharedPreferences?.edit()?.putString(KEY_ANNOUNCEMENTS, json)?.apply()
    }
}
