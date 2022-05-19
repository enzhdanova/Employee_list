package com.example.task.mainactivity.ui

import com.example.task.mainactivity.data.MockeData
import com.example.task.mainactivity.data.User
import com.example.task.mainactivity.ui.data.UIModel
import com.example.task.mainactivity.ui.data.UserItem
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType
import java.time.LocalDate

class EmployeesUseCase {
    private val listOfEmloyees = MockeData.users
    private val nowDay = LocalDate.now()

    private fun getUsersFromDepartment(departments: Departments): List<User> {
        return if (departments == Departments.ALL) {
            listOfEmloyees
        } else {
            listOfEmloyees.filter {
                it.department == departments.name.lowercase()
            }
        }
    }


    private fun getSortedByAlphabetList(users: List<User>): List<User> {
        val tmp = users.toMutableList()

        return tmp.sortedBy { it.lastName; it.firstName }
    }

    private fun getSortedByBDList(users: List<User>) = users.sortedBy {
        it.birthday.dayOfMonth; it.birthday.month
    }

    fun getEmploeeList(departments: Departments, sortType: SortType): List<UIModel> {
        val users = getUsersFromDepartment(departments)

        return when (sortType) {
            SortType.ALPHABET -> {
                val sortUser = getSortedByAlphabetList(users)
                getUIModelForUser(sortUser)
            }
            SortType.DATE_BIRTHDATE -> {
                getSortForNowDay(users)
            }
        }
    }

    private fun getUIModelForUserBD(users: List<User>): List<UIModel> = users.map {
        val item = userToUserItem(it)
        UIModel.UserWithBirthday(item)
    }


    private fun getUIModelForUser(users: List<User>): List<UIModel> = users.map {
        val item = userToUserItem(it)
        UIModel.User(item)
    }

    private fun getSortForNowDay(users: List<User>): List<UIModel> {
        val sortUser = getSortedByBDList(users)

        val usersBeforNowDay = sortUser.takeWhile {
            it.birthday.dayOfYear < nowDay.dayOfYear
        }

        val usersAfterNowDay = sortUser.takeLast(sortUser.size - usersBeforNowDay.size)

        val result = mutableListOf<UIModel>()
        result.addAll(getUIModelForUserBD(usersAfterNowDay))

        if (usersBeforNowDay.isNotEmpty()) {
            result.add(UIModel.Separator())
            result.addAll(getUIModelForUserBD(usersBeforNowDay))
        }

        return result
    }

    private fun userToUserItem(user: User) = UserItem(
        id = user.id,
        lastName = user.lastName,
        firstName = user.firstName,
        avatarUrl = user.avatarUrl,
        position = user.position,
        userTag = user.userTag,
        birthday = user.birthday,
         phone = user.phone
    )

}