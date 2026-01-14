package com.example.workpulse.managers

import android.content.Context
import android.content.SharedPreferences
import com.example.workpulse.models.LeaveRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * A singleton object to manage leave requests persistently.
 */
object LeaveManager {

    private const val PREF_NAME = "LeaveStore"
    private const val KEY_LEAVE = "leave_list"
    private val gson = Gson()
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    private fun getLeaveList(): MutableList<LeaveRequest> {
        val json = sharedPreferences?.getString(KEY_LEAVE, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<LeaveRequest>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    private fun saveLeaveList(list: List<LeaveRequest>) {
        val json = gson.toJson(list)
        sharedPreferences?.edit()?.putString(KEY_LEAVE, json)?.apply()
    }

    /**
     * Adds a new leave request.
     */
    fun addLeaveRequest(request: LeaveRequest) {
        val list = getLeaveList()
        list.add(0, request.copy(status = "PENDING"))
        saveLeaveList(list)
    }

    /**
     * Updates the status of a leave request.
     */
    fun updateLeaveStatus(leaveRequest: LeaveRequest, newStatus: String) {
        val list = getLeaveList()
        val index = list.indexOfFirst { 
            it.employeeEmail == leaveRequest.employeeEmail && 
            it.fromDate == leaveRequest.fromDate && 
            it.leaveType == leaveRequest.leaveType 
        }
        if (index != -1) {
            list[index] = list[index].copy(status = newStatus)
            saveLeaveList(list)
        }
    }

    /**
     * Deletes a leave request.
     */
    fun deleteLeaveRequest(leaveRequest: LeaveRequest) {
        val list = getLeaveList()
        list.removeAll { 
            it.employeeEmail == leaveRequest.employeeEmail && 
            it.fromDate == leaveRequest.fromDate && 
            it.leaveType == leaveRequest.leaveType 
        }
        saveLeaveList(list)
    }

    /**
     * Returns the complete list of all leave requests.
     */
    fun getAllLeaveRequests(): List<LeaveRequest> {
        return getLeaveList()
    }

    /**
     * Returns a filtered list of leave requests for a specific employee.
     */
    fun getLeaveRequestsForEmployee(employeeEmail: String): List<LeaveRequest> {
        return getLeaveList().filter { it.employeeEmail.equals(employeeEmail, ignoreCase = true) }
    }
}
