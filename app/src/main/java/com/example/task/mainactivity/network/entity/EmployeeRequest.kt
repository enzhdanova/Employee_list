package com.example.task.mainactivity.network.entity

import com.example.task.mainactivity.data.model.Employees
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
    val birthday: LocalDate,
    val phone: String
) {
    fun toModel() = Employees(
        id, avatarUrl, firstName,
        lastName, userTag, department,
        position, birthday, phone
    )
}