package com.example.task.mainactivity.data

import com.example.task.mainactivity.BuildConfig
import com.example.task.mainactivity.data.model.Employees
import com.example.task.mainactivity.domain.EmployeesRepository
import com.example.task.mainactivity.network.EmployeesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class EmployeesRepositoryImpl @Inject constructor(
    private val employeesApi: EmployeesApi
) : EmployeesRepository {

    override suspend fun getUsers(): Result<List<Employees>> {
        return withContext(Dispatchers.IO) {
            try {
               /* val response = employeesApi.getEmployees().body() ?: emptyList()

                val list = response.map {
                        it.toModel()
                    }
*/
                val list = MockeData.employees
                Result.success(list)
            } catch (io: Exception) {
                Result.failure(io)
            }
        }
    }
}