package com.example.workpulse.managers

import android.content.Context
import android.content.SharedPreferences

object EmployeeSessionManager {

    private const val PREF_NAME = "EmployeeSession"
    private const val KEY_EMAIL = "employee_email"
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveSession(email: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(KEY_EMAIL, email)
        editor?.apply()
    }

    fun getEmail(): String? {
        return sharedPreferences?.getString(KEY_EMAIL, null)
    }

    fun clearSession() {
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }
}
