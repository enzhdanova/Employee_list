package com.example.task.mainactivity.ui.viewmodel

import com.example.task.mainactivity.data.User
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType

data class EmployeesUIState(
    val employeeList: List<User>? = null,
    val sortType: SortType = SortType.ALPHABET,
    val departments: Departments = Departments.ALL,
    val error: Boolean = false
)