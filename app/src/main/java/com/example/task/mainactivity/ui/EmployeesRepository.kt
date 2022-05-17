package com.example.task.mainactivity.ui

import com.example.task.mainactivity.data.MockeData
import com.example.task.mainactivity.data.User
import com.example.task.mainactivity.utils.Departments
import java.time.LocalDate

class EmployeesRepository {
    private val listOfEmloyees = MockeData.users

    fun getUsersFromDepartment(departments: Departments) : List<User> {
        return if (departments == Departments.ALL) {
            listOfEmloyees
        }
         else {
            listOfEmloyees.filter {
                it.department == departments.name.lowercase()
            }
        }
    }

    //TODO: Думаю это должно быть в слое Domain

    fun getSortedByAlphabetList(users: List<User>) : List<User> {
        val tmp = users.toMutableList()

        return tmp.sortedBy { it.lastName; it.firstName }
    }

    fun getSortedByBDList(users: List<User>) : List<User> {
        val nowDay = LocalDate.now().dayOfMonth
        val nowMonth = LocalDate.now().month

         return users.sortedBy {
            it.birthday.dayOfMonth; it.birthday.month
        }
    }
}