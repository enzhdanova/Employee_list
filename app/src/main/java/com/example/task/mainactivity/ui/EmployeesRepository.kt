package com.example.task.mainactivity.ui

import com.example.task.mainactivity.data.MockeData
import com.example.task.mainactivity.data.User
import com.example.task.mainactivity.utils.Departments

class EmployeesRepository {
    private val listOfEmloyees = MockeData.users

    fun getAllUsers() = listOfEmloyees

    fun getUsersFromDepartment(departments: Departments) : List<User> {
        return listOfEmloyees.filter {
            it.department == departments.name.lowercase()
        }
    }
}