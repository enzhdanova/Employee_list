package com.example.task.mainactivity.data.model

import java.time.LocalDate

data class Employees(
    val id: String,
    val avatarUrl: String,
    val firstName: String,
    val lastName: String,
    val userTag: String,
    val department: String,
    val position: String,
    val birthday: LocalDate,
    val phone: String
)