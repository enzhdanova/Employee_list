package com.example.task.mainactivity.data

import java.time.LocalDate
import java.util.*

data class User(
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