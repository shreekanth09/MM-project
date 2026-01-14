package com.example.workpulse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.R
import com.example.workpulse.models.Announcement

class AnnouncementAdapter(
    private val announcements: List<Announcement>,
    private val onDeleteClick: (Announcement) -> Unit
) : RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val announcement = announcements[position]
        holder.title.text = announcement.title
        holder.message.text = announcement.message
        holder.date.text = announcement.date
        
        holder.ivDelete.setOnClickListener {
            onDeleteClick(announcement)
        }
    }

    override fun getItemCount() = announcements.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvAnnouncementTitle)
        val message: TextView = itemView.findViewById(R.id.tvAnnouncementMessage)
        val date: TextView = itemView.findViewById(R.id.tvAnnouncementDate)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
    }
}
