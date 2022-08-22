package com.example.task.mainactivity.ui.viewmodel

import com.example.task.mainactivity.domain.entity.UIModel
import com.example.task.mainactivity.utils.Department
import com.example.task.mainactivity.utils.SortType

data class EmployeesUIState(
    val employeeList: List<UIModel>? = null,
    val sortType: SortType = SortType.ALPHABET,
    val department: Department = Department.ALL,
    val filter: String = "",
    val error: Boolean = false,
    val needUpdateList: Boolean = true
)