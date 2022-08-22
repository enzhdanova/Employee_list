package com.example.task.mainactivity.domain

import com.example.task.mainactivity.data.model.Employee

interface EmployeesRepository {
    suspend fun getEmployees(): Result<List<Employee>>
}