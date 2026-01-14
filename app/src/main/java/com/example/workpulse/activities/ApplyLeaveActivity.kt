package com.example.workpulse.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.R
import com.example.workpulse.databinding.ActivityApplyLeaveBinding
import com.example.workpulse.managers.EmployeeSessionManager
import com.example.workpulse.managers.LeaveManager
import com.example.workpulse.models.LeaveRequest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ApplyLeaveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplyLeaveBinding
    private val fromCalendar: Calendar = Calendar.getInstance()
    private val toCalendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyLeaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupClickListeners()
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.leave_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerLeaveType.adapter = adapter
        }
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener { finish() }

        binding.tvFromDate.setOnClickListener {
            showDatePickerDialog(fromCalendar) { updateDateInView(binding.tvFromDate, fromCalendar) }
        }

        binding.tvToDate.setOnClickListener {
            showDatePickerDialog(toCalendar) { updateDateInView(binding.tvToDate, toCalendar) }
        }

        binding.btnSubmitLeave.setOnClickListener {
            if (validateInput()) {
                saveLeaveRequest()
            }
        }
    }

    private fun saveLeaveRequest() {
        val employeeEmail = EmployeeSessionManager.getEmail()
        if (employeeEmail == null) {
            Toast.makeText(this, "Error: Employee not logged in.", Toast.LENGTH_LONG).show()
            return
        }

        val leaveType = binding.spinnerLeaveType.selectedItem.toString()
        val description = binding.etLeaveDescription.text.toString()
        val fromDate = binding.tvFromDate.text.toString()
        val toDate = binding.tvToDate.text.toString()

        val newLeaveRequest = LeaveRequest(
            employeeEmail = employeeEmail,
            leaveType = leaveType,
            description = description,
            fromDate = fromDate,
            toDate = toDate,
            status = "PENDING"
        )

        LeaveManager.addLeaveRequest(newLeaveRequest)

        Toast.makeText(this, "Leave request submitted!", Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun showDatePickerDialog(calendar: Calendar, onDateSet: () -> Unit) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            onDateSet()
        }

        DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateInView(textView: android.widget.TextView, calendar: Calendar) {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView.text = sdf.format(calendar.time)
    }

    private fun validateInput(): Boolean {
        if (binding.spinnerLeaveType.selectedItemPosition == 0) { // Assuming the first item is a prompt
            Toast.makeText(this, "Please select a leave type.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.tvFromDate.text == "Select Date" || binding.tvToDate.text == "Select Date") {
            Toast.makeText(this, "Please select both start and end dates.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
