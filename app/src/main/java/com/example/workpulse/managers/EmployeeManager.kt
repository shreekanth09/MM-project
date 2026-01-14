package com.example.workpulse.managers

import android.content.Context
import android.content.SharedPreferences
import com.example.workpulse.models.Employee
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object EmployeeManager {

    private const val PREF_NAME = "EmployeeStore"
    private const val KEY_EMPLOYEES = "employee_list"
    private val gson = Gson()
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun getEmployees(): List<Employee> {
        val json = sharedPreferences?.getString(KEY_EMPLOYEES, null)
        return if (json != null) {
            val type = object : TypeToken<List<Employee>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addEmployee(employee: Employee) {
        val currentEmployees = getEmployees().toMutableList()
        currentEmployees.add(0, employee)
        saveEmployees(currentEmployees)
    }

    fun deleteEmployee(email: String) {
        val currentEmployees = getEmployees().toMutableList()
        currentEmployees.removeAll { it.email == email }
        saveEmployees(currentEmployees)
    }

    private fun saveEmployees(employees: List<Employee>) {
        val json = gson.toJson(employees)
        sharedPreferences?.edit()?.putString(KEY_EMPLOYEES, json)?.apply()
    }
}
