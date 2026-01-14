package com.example.workpulse.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.workpulse.fragments.AdminAttendanceFragment
import com.example.workpulse.fragments.AttendanceFragment
import com.example.workpulse.fragments.LeaveRequestsFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val userRole: String?
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2 // We have two tabs: Attendance and Leave Requests
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                // If the user is an ADMIN, show the admin-specific fragment.
                // Otherwise, show the regular employee attendance fragment.
                if (userRole == "ADMIN") {
                    AdminAttendanceFragment()
                } else {
                    AttendanceFragment()
                }
            }
            1 -> LeaveRequestsFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
