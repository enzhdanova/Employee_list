package com.example.task.mainactivity.network

import com.example.task.mainactivity.network.entity.ItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface EmployeesApi {
    @Headers("Prefer: code=200, example=success")
    @GET("users")
    suspend fun getEmployees(): Response<ItemsResponse>
}