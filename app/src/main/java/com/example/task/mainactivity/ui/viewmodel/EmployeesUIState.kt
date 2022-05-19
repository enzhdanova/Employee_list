package com.example.task.mainactivity.ui.viewmodel

import com.example.task.mainactivity.ui.data.UIModel
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType

data class EmployeesUIState(
    val employeeList: List<UIModel>? = null,
    val sortType: SortType = SortType.ALPHABET,
    val departments: Departments = Departments.ALL,
    val filter: String = "",
    val error: Boolean = false
)