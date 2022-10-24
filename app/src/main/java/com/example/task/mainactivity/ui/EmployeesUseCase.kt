package com.example.task.mainactivity.ui

import com.example.task.mainactivity.data.model.Employee
import com.example.task.mainactivity.domain.entity.EmployeeItem
import com.example.task.mainactivity.domain.entity.UIModel
import com.example.task.mainactivity.utils.Department
import com.example.task.mainactivity.utils.SortType

interface EmployeesUseCase {
    fun getCurrentEmployeeList(
        department: Department,
        sortType: SortType,
        filterString: String
    ): Result<List<UIModel>>

    suspend fun fetchEmployees(
        department: Department,
        sortType: SortType,
        filterString: String
    ): Result<Boolean>

    suspend fun getEmployee(id: String): Result<EmployeeItem>
}