package com.example.workpulse.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.workpulse.R
import com.example.workpulse.databinding.ItemTaskBinding
import com.example.workpulse.models.Task

class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val onDeleteClick: (Task) -> Unit,
    private val onStatusChanged: (task: Task, newStatus: String) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTaskTitle.text = task.title
            binding.tvTaskDescription.text = task.description
            binding.tvAssignedTo.text = "Assigned To: ${task.assignedEmployeeEmail}"
            binding.tvDueDate.text = "Due Date: ${task.dueDate}"
            binding.tvTaskStatus.text = task.status.replace("_", " ")

            binding.tvTaskStatus.setTextColor(
                when (task.status.uppercase()) {
                    "COMPLETED" -> Color.GREEN
                    "IN_PROGRESS" -> Color.BLUE
                    "OVERDUE" -> Color.RED
                    else -> Color.GRAY
                }
            )

            binding.ivDeleteTask.setOnClickListener {
                onDeleteClick(task)
            }

            itemView.setOnClickListener { view ->
                val popup = PopupMenu(view.context, view)
                popup.menuInflater.inflate(R.menu.task_status_menu, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    val newStatus = when (item.itemId) {
                        R.id.status_assigned -> "ASSIGNED"
                        R.id.status_in_progress -> "IN_PROGRESS"
                        R.id.status_completed -> "COMPLETED"
                        else -> null
                    }

                    if (newStatus != null) {
                        onStatusChanged(task, newStatus)
                    }
                    true
                }
                popup.show()
            }
        }
    }
}
