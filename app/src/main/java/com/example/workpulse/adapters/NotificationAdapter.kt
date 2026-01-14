package com.example.workpulse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.databinding.ItemNotificationBinding
import com.example.workpulse.models.Notification

class NotificationAdapter(private var notifications: List<Notification>) : 
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {
            binding.tvNotificationTitle.text = notification.title
            binding.tvNotificationMessage.text = notification.message
            binding.tvNotificationTime.text = notification.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount() = notifications.size

    fun updateList(newList: List<Notification>) {
        notifications = newList
        notifyDataSetChanged()
    }
}
