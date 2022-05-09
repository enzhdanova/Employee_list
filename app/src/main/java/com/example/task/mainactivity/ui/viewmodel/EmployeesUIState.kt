package com.example.task.mainactivity.ui.viewmodel

import com.example.task.mainactivity.data.User

data class EmployeesUIState(
    val employeeList: List<User>? = null,
    val error: Boolean = false
)