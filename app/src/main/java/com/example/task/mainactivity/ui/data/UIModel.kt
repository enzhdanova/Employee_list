package com.example.task.mainactivity.ui.data

import java.time.LocalDate
import java.util.*

sealed class UIModel {
    data class User(
        val item: UserItem
    ) : UIModel()

    data class UserWithBirthday(
        val item: UserWithBirthdayItem
    ) : UIModel()

    data class Separator(
        val year: String
    )  : UIModel()
}

data class UserItem(
    val id: String,
    val avatarUrl: String,
    val firstName: String,
    val lastName: String,
    val userTag: String,
    val position: String,
)

data class UserWithBirthdayItem(
    val userItem: UserItem,
    val birthday: LocalDate
)

