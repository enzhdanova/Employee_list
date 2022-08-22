package com.example.task.mainactivity.domain.entity

import com.example.task.mainactivity.data.model.Employee
import java.time.LocalDate

sealed class UIModel {
    data class EmployeeUI(
        val item: EmployeeItem
    ) : UIModel() {
        companion object {
            fun toUIModel(employee: Employee) = EmployeeUI(
                item = EmployeeItem.toUIModel(employee)
            )
        }
    }

    data class EmployeeUIWithBirthday(
        val item: EmployeeItem
    ) : UIModel() {
        companion object {
            fun toUIModel(employee: Employee) = EmployeeUI(
                item = EmployeeItem.toUIModel(employee)
            )
        }
    }

    data class Separator(
        val year: String = ""
    ) : UIModel()

    object NotFound : UIModel()
}

data class EmployeeItem(
    val id: String,
    val avatarUrl: String,
    val firstName: String,
    val lastName: String,
    val userTag: String,
    val position: String,
    val birthday: LocalDate,
    val phone: String
) {

    companion object {
        fun toUIModel(employee: Employee) = EmployeeItem(
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
