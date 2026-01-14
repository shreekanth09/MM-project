package com.example.workpulse.app

import android.app.Application
import com.example.workpulse.managers.AnnouncementManager
import com.example.workpulse.managers.EmployeeManager
import com.example.workpulse.managers.EmployeeSessionManager
import com.example.workpulse.managers.TaskManager

class WorkPulseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        EmployeeSessionManager.init(this)
        EmployeeManager.init(this)
        TaskManager.init(this)
        AnnouncementManager.init(this)
    }
}
