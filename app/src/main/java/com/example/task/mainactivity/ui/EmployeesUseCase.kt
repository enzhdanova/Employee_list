package com.example.task.mainactivity.ui

import com.example.task.mainactivity.domain.entity.UIModel
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType

interface EmployeesUseCase {
    fun getCurrentEmployeeList(
        departments: Departments,
        sortType: SortType,
        filterString: String
    ): Result<List<UIModel>>

    suspend fun fetchEmployees(
        departments: Departments,
        sortType: SortType,
        filterString: String
    ): Result<Boolean>
}