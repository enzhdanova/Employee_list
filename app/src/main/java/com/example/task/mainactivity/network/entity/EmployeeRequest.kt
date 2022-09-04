package com.example.task.mainactivity.network.entity

import com.example.task.mainactivity.data.model.Employee
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class EmployeeRequest(
    val id: String,
    val avatarUrl: String,
    val firstName: String,
    val lastName: String,
    val userTag: String,
    val department: String,
    val position: String,
    val birthday: String,
    val phone: String
) {
    fun toModel() = Employee(
        id, avatarUrl, firstName,
        lastName, userTag, department,
        position, LocalDate.parse(birthday), phone
    )
}