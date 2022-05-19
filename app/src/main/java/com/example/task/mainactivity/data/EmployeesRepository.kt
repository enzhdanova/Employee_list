package com.example.task.mainactivity.data

class EmployeesRepository {
    private val listOfEmloyees = MockeData.users

    fun getUsers() = listOfEmloyees

}