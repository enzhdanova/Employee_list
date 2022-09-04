package com.example.task.mainactivity.data

import com.example.task.mainactivity.data.model.Employee
import com.example.task.mainactivity.domain.EmployeesRepository
import com.example.task.mainactivity.network.EmployeesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class EmployeesRepositoryImpl @Inject constructor(
    private val employeesApi: EmployeesApi
) : EmployeesRepository {

    override suspend fun getEmployees(): Result<List<Employee>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = employeesApi.getEmployees().body()?.items ?: emptyList()
                val employees = response.map {
                    it.toModel()
                }
                Result.success(employees)
            } catch (io: Exception) {
                Result.failure(io)
            }
        }
    }
}