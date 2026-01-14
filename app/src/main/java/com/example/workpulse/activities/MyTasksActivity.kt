package com.example.workpulse.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workpulse.adapters.TaskAdapter
import com.example.workpulse.databinding.ActivityMyTasksBinding
import com.example.workpulse.managers.EmployeeSessionManager
import com.example.workpulse.managers.TaskManager
import com.example.workpulse.models.Task
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyTasksBinding
    private lateinit var adapter: TaskAdapter
    private var allTasks: List<Task> = emptyList()
    private val tabStatuses = listOf("Assigned", "In Progress", "Completed", "Overdue")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        setupRecyclerView()
        setupTabs()
    }

    override fun onResume() {
        super.onResume()
        loadAndFilterTasks()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(
            mutableListOf(),
            onDeleteClick = { task ->
                showDeleteConfirmation(task)
            },
            onStatusChanged = { task, newStatus ->
                TaskManager.updateTaskStatus(task, newStatus)
                
                // When a task status is changed, find the corresponding tab and select it.
                val tabIndex = tabStatuses.indexOfFirst { it.equals(newStatus.replace("_", " "), ignoreCase = true) }
                if (tabIndex != -1) {
                    binding.tabLayout.getTabAt(tabIndex)?.select()
                }
                loadAndFilterTasks()
            }
        )
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter
    }

    private fun showDeleteConfirmation(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete") { _, _ ->
                TaskManager.deleteTask(task)
                loadAndFilterTasks()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupTabs() {
        tabStatuses.forEach { status ->
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(status))
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                loadAndFilterTasks()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun loadAndFilterTasks() {
        val loggedInEmployeeEmail = EmployeeSessionManager.getEmail()?.trim()

        if (loggedInEmployeeEmail == null) {
            Toast.makeText(this, "Error: Employee not logged in.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        allTasks = TaskManager.getTasks().filter {
            it.assignedEmployeeEmail.equals(loggedInEmployeeEmail, ignoreCase = true)
        }

        val selectedTab = binding.tabLayout.getTabAt(binding.tabLayout.selectedTabPosition)
        val currentStatus = selectedTab?.text.toString()

        val filteredTasks = when (currentStatus) {
            "Overdue" -> {
                val today = Date()
                val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                allTasks.filter { task ->
                    if (task.status == "COMPLETED") false
                    else {
                        try {
                            val dueDate = sdf.parse(task.dueDate)
                            dueDate != null && today.after(dueDate)
                        } catch (e: Exception) {
                            false
                        }
                    }
                }
            }
            else -> {
                val statusForFilter = currentStatus.replace(" ", "_")
                allTasks.filter { it.status.equals(statusForFilter, ignoreCase = true) }
            }
        }

        adapter.updateTasks(filteredTasks.toMutableList())

        if (filteredTasks.isEmpty()) {
            Toast.makeText(this, "No tasks with status: $currentStatus", Toast.LENGTH_SHORT).show()
        }
    }

    // Helper to update the adapter's list
    private fun TaskAdapter.updateTasks(newTasks: MutableList<Task>) {
        // We'll just recreate the adapter in loadAndFilterTasks for simplicity in this case, 
        // or add a method to TaskAdapter. Let's add a method to TaskAdapter for efficiency.
    }
}
