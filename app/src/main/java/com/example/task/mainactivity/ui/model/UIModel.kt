package com.example.task.mainactivity.ui.model

import com.example.task.mainactivity.data.model.User
import java.time.LocalDate

sealed class UIModel {
    data class User(
        val item: UserItem
    ) : UIModel()

    data class UserWithBirthday(
        val item: UserItem
    ) : UIModel()

    data class Separator(
        val year: String = ""
    ) : UIModel()

    object NotFound : UIModel()
}

data class UserItem(
    val id: String,
    val avatarUrl: String,
    val firstName: String,
    val lastName: String,
    val userTag: String,
    val position: String,
    val birthday: LocalDate,
    val phone: String
) {
    fun toUser() = User(
        id = id,
        avatarUrl = avatarUrl,
        firstName = firstName,
        lastName = lastName,
        userTag = userTag,
        position = position,
        birthday = birthday,
        phone = phone,
        department = ""
    )
}
