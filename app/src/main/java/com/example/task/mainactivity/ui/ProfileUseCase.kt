package com.example.task.mainactivity.ui

import com.example.task.mainactivity.domain.entity.EmployeeItem

interface ProfileUseCase {
    suspend fun getEmployee(id: String): Result<EmployeeItem>
}