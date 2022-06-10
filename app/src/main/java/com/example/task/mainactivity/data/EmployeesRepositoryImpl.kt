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

    override suspend fun getUsers(): Result<List<Employee>> {
        return withContext(Dispatchers.IO) {
            try {
                val list =  MockeData.employees
                /*employeesApi.getEmployees().map {
                    it.toModel()
                }*/

                Result.success(list)
            } catch (io: Exception) {
                Result.failure(io)
            }
        }
    }
}