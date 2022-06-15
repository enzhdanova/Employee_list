package com.example.task.mainactivity.network

import com.example.task.mainactivity.network.entity.EmployeeRequest
import retrofit2.Response
import retrofit2.http.GET

interface EmployeesApi {
    @GET("users/")
    suspend fun getEmployees(): Response<List<EmployeeRequest>>
}