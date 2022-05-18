package com.example.task.mainactivity.ui

import com.example.task.mainactivity.data.MockeData
import com.example.task.mainactivity.data.User
import com.example.task.mainactivity.ui.data.UIModel
import com.example.task.mainactivity.ui.data.UserItem
import com.example.task.mainactivity.ui.data.UserWithBirthdayItem
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType
import java.time.LocalDate

class EmployeesRepository {
    private val listOfEmloyees = MockeData.users

    fun getUsersFromDepartment(departments: Departments): List<User> {
        return if (departments == Departments.ALL) {
            listOfEmloyees
        } else {
            listOfEmloyees.filter {
                it.department == departments.name.lowercase()
            }
        }
    }

    //TODO: Думаю это должно быть в слое Domain

    fun getSortedByAlphabetList(users: List<User>): List<User> {
        val tmp = users.toMutableList()

        return tmp.sortedBy { it.lastName; it.firstName }
    }

    fun getSortedByBDList(users: List<User>): List<User> {
        val nowDay = LocalDate.now().dayOfMonth
        val nowMonth = LocalDate.now().month

        return users.sortedBy {
            it.birthday.dayOfMonth; it.birthday.month
        }
    }

    fun getEmploeeList(departments: Departments, sortType: SortType): List<UIModel> {
        val users = getUsersFromDepartment(departments)

        return when (sortType) {
            SortType.ALPHABET -> {
                val sortUser = getSortedByAlphabetList(users)
                //TODO: тут скорее всего надо исправить, чтобы было toModel в классе и просто метод вызывать
                sortUser.map {
                    val item = userToUserItem(it)
                    UIModel.User(item)
                }
            }
            SortType.DATE_BIRTHDATE -> {
                val sortUser = getSortedByBDList(users)
                sortUser.map {
                    val item = userToUserItem(it)
                    UIModel.UserWithBirthday(UserWithBirthdayItem(item, it.birthday))
                }
            }
        }
    }

    private fun userToUserItem(user: User) = UserItem(
        id = user.id,
        lastName = user.lastName,
        firstName = user.firstName,
        avatarUrl = user.avatarUrl,
        position = user.position,
        userTag = user.userTag,
    )
}