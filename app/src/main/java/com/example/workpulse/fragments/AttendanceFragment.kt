package com.example.workpulse.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.workpulse.databinding.FragmentAttendanceBinding
import com.example.workpulse.managers.EmployeeSessionManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class AttendanceFragment : Fragment() {

    private var _binding: FragmentAttendanceBinding? = null
    private val binding get() = _binding!!
    private var employeeEmail: String? = null
    private val totalWorkingDays = 25

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Correctly inflate the binding here
        _binding = FragmentAttendanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        employeeEmail = EmployeeSessionManager.getEmail()

        if (employeeEmail == null) {
            Toast.makeText(requireContext(), "Error: Employee not logged in.", Toast.LENGTH_LONG).show()
            requireActivity().finish()
            return
        }

        binding.btnCheckIn.setOnClickListener { handleCheckIn() }
        binding.btnCheckOut.setOnClickListener { handleCheckOut() }
    }

    override fun onResume() {
        super.onResume()
        // Update UI every time the fragment is visible
        if (employeeEmail != null) {
            updateUiForToday()
        }
    }

    private fun handleCheckIn() {
        if (employeeEmail == null) return
        val prefs = requireActivity().getSharedPreferences("AttendancePrefs_${employeeEmail}", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val todayDate = getCurrentDate()

        val lastCheckInDate = prefs.getString("lastCheckInDate", null)
        if (todayDate != lastCheckInDate) {
            var presentDays = prefs.getInt("presentDays", 0)
            presentDays++
            editor.putInt("presentDays", presentDays)
            editor.putString("lastCheckInDate", todayDate)
        }

        val checkInTime = getCurrentTime()
        editor.putString("${todayDate}_CHECK_IN", checkInTime)
        editor.apply()

        Toast.makeText(requireContext(), "Checked in successfully", Toast.LENGTH_SHORT).show()
        updateUiForToday()
    }

    private fun handleCheckOut() {
        if (employeeEmail == null) return
        val prefs = requireActivity().getSharedPreferences("AttendancePrefs_${employeeEmail}", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val todayDate = getCurrentDate()
        val checkOutTime = getCurrentTime()

        editor.putString("${todayDate}_CHECK_OUT", checkOutTime)
        editor.apply()

        Toast.makeText(requireContext(), "Checked out successfully", Toast.LENGTH_SHORT).show()
        updateUiForToday()
    }

    private fun updateUiForToday() {
        if (employeeEmail == null) return

        val prefs = requireActivity().getSharedPreferences("AttendancePrefs_${employeeEmail}", Context.MODE_PRIVATE)
        val todayDate = getCurrentDate()
        val checkInTime = prefs.getString("${todayDate}_CHECK_IN", null)
        val checkOutTime = prefs.getString("${todayDate}_CHECK_OUT", null)

        if (checkInTime != null) {
            binding.tvCheckInTime.text = "Checked in at: $checkInTime, Date: $todayDate"
            binding.tvCheckInTime.visibility = View.VISIBLE
            binding.btnCheckIn.isEnabled = false
            binding.btnCheckOut.isEnabled = true
        } else {
            binding.btnCheckIn.isEnabled = true
            binding.btnCheckOut.isEnabled = false
        }

        if (checkOutTime != null) {
            binding.tvCheckOutTime.text = "Checked out at: $checkOutTime"
            binding.tvCheckOutTime.visibility = View.VISIBLE
            binding.btnCheckOut.isEnabled = false
        }

        val presentDays = prefs.getInt("presentDays", 0)
        val absentDays = totalWorkingDays - presentDays
        val attendancePercentage = if (totalWorkingDays > 0) (presentDays * 100) / totalWorkingDays else 0

        binding.tvPresentDays.text = presentDays.toString()
        binding.tvAbsentDays.text = absentDays.toString()
        binding.tvAttendancePercentage.text = "$attendancePercentage%"
    }

    private fun getSdf(format: String): SimpleDateFormat {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return sdf
    }

    private fun getCurrentDate(): String {
        return getSdf("yyyy-MM-dd").format(Date())
    }

    private fun getCurrentTime(): String {
        return getSdf("hh:mm a").format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
