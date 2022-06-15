package com.example.task.mainactivity.ui.model

import com.example.task.mainactivity.data.model.Employees
import java.time.LocalDate

sealed class UIModel {
    data class User(
        val item: UserItem
    ) : UIModel() {
        companion object {
            fun toUser(employee: Employees) = User(
                item = UserItem.toUIModel(employee)
            )
        }
    }

    data class UserWithBirthday(
        val item: UserItem
    ) : UIModel() {
        companion object {
            fun toUserWithBirthday(employee: Employees) = User(
                item = UserItem.toUIModel(employee)
            )
        }
    }

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
    fun toUser() = Employees(
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

    companion object {
        fun toUIModel(employee: Employees) = UserItem(
            id = employee.id,
            lastName = employee.lastName,
            firstName = employee.firstName,
            avatarUrl = employee.avatarUrl,
            position = employee.position,
            userTag = employee.userTag,
            birthday = employee.birthday,
            phone = employee.phone
        )
    }
}
