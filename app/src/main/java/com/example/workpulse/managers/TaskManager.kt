package com.example.workpulse.managers

import android.content.Context
import android.content.SharedPreferences
import com.example.workpulse.models.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TaskManager {

    private const val PREF_NAME = "TaskStore"
    private const val KEY_TASKS = "task_list"
    private val gson = Gson()
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun getTasks(): List<Task> {
        val json = sharedPreferences?.getString(KEY_TASKS, null)
        return if (json != null) {
            val type = object : TypeToken<List<Task>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addTask(task: Task) {
        val currentTasks = getTasks().toMutableList()
        currentTasks.add(0, task)
        saveTasks(currentTasks)
    }

    fun updateTaskStatus(taskToUpdate: Task, newStatus: String) {
        val currentTasks = getTasks().toMutableList()
        val taskIndex = currentTasks.indexOfFirst { it.title == taskToUpdate.title && it.assignedEmployeeEmail == taskToUpdate.assignedEmployeeEmail }
        if (taskIndex != -1) {
            currentTasks[taskIndex] = currentTasks[taskIndex].copy(status = newStatus)
            saveTasks(currentTasks)
        }
    }

    fun deleteTask(taskToDelete: Task) {
        val currentTasks = getTasks().toMutableList()
        currentTasks.removeAll { it.title == taskToDelete.title && it.assignedEmployeeEmail == taskToDelete.assignedEmployeeEmail }
        saveTasks(currentTasks)
    }

    fun saveTasks(tasks: List<Task>) {
        val json = gson.toJson(tasks)
        sharedPreferences?.edit()?.putString(KEY_TASKS, json)?.apply()
    }
}
