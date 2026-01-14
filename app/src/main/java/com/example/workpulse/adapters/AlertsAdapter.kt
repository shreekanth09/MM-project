package com.example.workpulse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.databinding.ItemAlertBinding
import com.example.workpulse.models.Alert // Assuming you will create this model

class AlertsAdapter(private var alerts: List<Alert>) : 
    RecyclerView.Adapter<AlertsAdapter.AlertViewHolder>() {

    inner class AlertViewHolder(private val binding: ItemAlertBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        fun bind(alert: Alert) {
            binding.tvAlertTitle.text = alert.title
            binding.tvAlertMessage.text = alert.message
            binding.tvAlertDateTime.text = alert.dateTime
            binding.tvAlertTag.text = alert.tag
            // Later, you can add logic to change tag color based on content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val binding = ItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.bind(alerts[position])
    }

    override fun getItemCount() = alerts.size

    // You can use this function to update the list later
    fun updateAlerts(newAlerts: List<Alert>) {
        alerts = newAlerts
        notifyDataSetChanged()
    }
}
