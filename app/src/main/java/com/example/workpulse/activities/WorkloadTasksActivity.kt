package com.example.workpulse.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workpulse.adapters.TaskAdapter
import com.example.workpulse.databinding.ActivityWorkloadTasksBinding
import com.example.workpulse.managers.TaskManager
import com.example.workpulse.models.Task
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WorkloadTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkloadTasksBinding
    private var allTasks: List<Task> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkloadTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
        setupTabs()
    }

    override fun onResume() {
        super.onResume()
        loadAndProcessTasks()
    }

    private fun loadAndProcessTasks() {
        var tasks = TaskManager.getTasks()
        val today = Date()
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        var tasksModified = false

        tasks = tasks.map { task ->
            if (task.status != "COMPLETED") {
                try {
                    val dueDate = sdf.parse(task.dueDate)
                    if (dueDate != null && today.after(dueDate)) {
                        if (task.status != "OVERDUE") {
                            tasksModified = true
                            return@map task.copy(status = "OVERDUE")
                        }
                    }
                } catch (e: Exception) {
                    // Ignore
                }
            }
            task
        }

        if (tasksModified) {
            TaskManager.saveTasks(tasks)
        }

        allTasks = tasks
        val currentTab = binding.tabLayout.getTabAt(binding.tabLayout.selectedTabPosition)
        filterTasksByStatus(currentTab?.text.toString())
    }

    private fun setupRecyclerView() {
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener { finish() }

        binding.btnCreateTask.setOnClickListener {
            startActivity(Intent(this, CreateTaskActivity::class.java))
        }
    }

    private fun setupTabs() {
        binding.tabLayout.removeAllTabs()
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("ASSIGNED"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("IN_PROGRESS"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("COMPLETED"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("OVERDUE"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                filterTasksByStatus(tab?.text.toString())
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun filterTasksByStatus(status: String) {
        val filteredTasks = allTasks.filter { it.status.equals(status, ignoreCase = true) }

        val newAdapter = TaskAdapter(
            filteredTasks.toMutableList(),
            onDeleteClick = { task ->
                showDeleteConfirmation(task)
            },
            onStatusChanged = { task, newStatus ->
                TaskManager.updateTaskStatus(task, newStatus)
                loadAndProcessTasks()
            }
        )
        binding.rvTasks.adapter = newAdapter
    }

    private fun showDeleteConfirmation(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete") { _, _ ->
                TaskManager.deleteTask(task)
                loadAndProcessTasks()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
