package com.example.workpulse.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.databinding.ActivityCreateTaskBinding
import com.example.workpulse.managers.EmployeeManager
import com.example.workpulse.managers.TaskManager
import com.example.workpulse.models.Employee
import com.example.workpulse.models.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTaskBinding
    private val calendar: Calendar = Calendar.getInstance()
    private var allEmployees: List<Employee> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        allEmployees = EmployeeManager.getEmployees()
        val employeeNames = allEmployees.map { it.name }

        if (employeeNames.isEmpty()) {
            Toast.makeText(this, "No employees found. Please add an employee first.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, employeeNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAssignTo.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener { finish() }

        binding.tvDueDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnSaveTask.setOnClickListener {
            if (validateInput()) {
                val newTask = createTaskFromInput()
                TaskManager.addTask(newTask)
                Toast.makeText(this, "Task Saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun createTaskFromInput(): Task {
        val title = binding.etTaskTitle.text.toString().trim()
        val description = binding.etTaskDescription.text.toString().trim()
        val selectedEmployeeIndex = binding.spinnerAssignTo.selectedItemPosition
        val assignedEmployee = allEmployees[selectedEmployeeIndex]
        val dueDate = binding.tvDueDate.text.toString()
        val selectedPriorityId = binding.rgPriority.checkedRadioButtonId
        val priority = findViewById<RadioButton>(selectedPriorityId).text.toString()

        return Task(
            title = title,
            description = description,
            assignedEmployeeName = assignedEmployee.name,
            // Corrected: Standardize email to prevent matching issues.
            assignedEmployeeEmail = assignedEmployee.email.trim(),
            dueDate = dueDate,
            priority = priority
        )
    }

    private fun showDatePickerDialog() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvDueDate.text = sdf.format(calendar.time)
    }

    private fun validateInput(): Boolean {
        val title = binding.etTaskTitle.text.toString().trim()
        val dueDate = binding.tvDueDate.text.toString()

        if (title.isEmpty()) {
            binding.etTaskTitle.error = "Task Title is required"
            return false
        }

        if (binding.spinnerAssignTo.selectedItem == null) {
            Toast.makeText(this, "Please assign the task to an employee.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (dueDate == "Select Date") {
            Toast.makeText(this, "Please select a due date.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
