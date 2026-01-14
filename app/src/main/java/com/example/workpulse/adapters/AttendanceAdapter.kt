package com.example.workpulse.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.R
import com.example.workpulse.models.AttendanceRecord

class AttendanceAdapter(private var attendanceList: List<AttendanceRecord>) : RecyclerView.Adapter<AttendanceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attendance_record, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = attendanceList[position]
        holder.employeeName.text = record.employeeName
        holder.attendanceStatus.text = record.status

        if (record.status.equals("Present", ignoreCase = true)) {
            holder.attendanceStatus.setTextColor(Color.GREEN)
        } else if (record.status.equals("Absent", ignoreCase = true)) {
            holder.attendanceStatus.setTextColor(Color.RED)
        }
    }

    override fun getItemCount() = attendanceList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAttendance(newAttendanceList: List<AttendanceRecord>) {
        attendanceList = newAttendanceList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val employeeName: TextView = itemView.findViewById(R.id.tvEmployeeName)
        val attendanceStatus: TextView = itemView.findViewById(R.id.tvAttendanceStatus)
    }
}