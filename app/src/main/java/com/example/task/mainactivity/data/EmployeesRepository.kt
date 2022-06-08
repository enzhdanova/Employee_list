package com.example.task.mainactivity.data

import com.example.task.mainactivity.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class EmployeesRepository {
    private val listOfEmloyees = MockeData.users

    suspend fun getUsers(): Result<List<User>> {
        return withContext(Dispatchers.IO) {
            try {
                val list = listOfEmloyees
                Result.success(list)
            } catch (io: Exception){
                Result.failure(io)
            }

        }
    }

}