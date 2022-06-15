package com.example.task.mainactivity.domain

import com.example.task.mainactivity.data.model.Employees

interface EmployeesRepository {
    suspend fun getUsers(): Result<List<Employees>>
}