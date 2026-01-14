package com.example.workpulse.models

/**
 * A singleton object to act as a shared, in-memory data source for employees.
 * This ensures all parts of the app access the same list of employees.
 */
object EmployeeStore {
    val employees = mutableListOf<Employee>()
}
