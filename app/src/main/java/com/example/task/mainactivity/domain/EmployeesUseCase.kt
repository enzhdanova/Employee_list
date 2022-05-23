package com.example.task.mainactivity.domain

import com.example.task.mainactivity.data.EmployeesRepository
import com.example.task.mainactivity.data.model.User
import com.example.task.mainactivity.ui.data.UIModel
import com.example.task.mainactivity.ui.data.UserItem
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType
import java.time.LocalDate

class EmployeesUseCase(
    private val employeesRepository: EmployeesRepository
) {

    private fun getUsersFromDepartment(departments: Departments): List<User> {
        return if (departments == Departments.ALL) {
            employeesRepository.getUsers()
        } else {
            employeesRepository.getUsers().filter {
                it.department == departments.name.lowercase()
            }
        }
    }

    fun getEmployeeList(
        departments: Departments,
        sortType: SortType,
        filterString: String
    ): List<UIModel> {
        val users = getUsersFromDepartment(departments).filter {
            it.lastName.contains(filterString, true)
                    || it.firstName.contains(
                filterString,
                true
            ) || it.userTag.contains(filterString, true)
        }

        return when (sortType) {
            SortType.ALPHABET -> {
                users.sortedByAlphabet().getUIModelForUser()
            }
            SortType.DATE_BIRTHDATE -> {
                getSortForNowDay(users)
            }
        }
    }

    private fun getSortForNowDay(users: List<User>): List<UIModel> {
        val sortUser = users.sortedByBirthdate()

        val usersBeforeNowDay = sortUser.takeWhile {
            it.birthday.beforeNowDay()
        }

        val usersAfterNowDay = sortUser.takeLast(sortUser.size - usersBeforeNowDay.size)

        val result = mutableListOf<UIModel>()
        result.addAll(usersAfterNowDay.getUIModelForUserWithBd())

        if (usersBeforeNowDay.isNotEmpty()) {
            result.add(UIModel.Separator())
            result.addAll(usersBeforeNowDay.getUIModelForUserWithBd())
        }

        return result
    }

    private fun toUIModel(user: User) = UserItem(
        id = user.id,
        lastName = user.lastName,
        firstName = user.firstName,
        avatarUrl = user.avatarUrl,
        position = user.position,
        userTag = user.userTag,
        birthday = user.birthday,
        phone = user.phone
    )

    private fun List<User>.getUIModelForUser(): List<UIModel> =
        this.map { user ->
            val item = toUIModel(user)
            UIModel.User(item)
        }


    private fun List<User>.getUIModelForUserWithBd(): List<UIModel> =
        this.map { user ->
            val item = toUIModel(user)
            UIModel.UserWithBirthday(item)
        }

    private fun List<User>.sortedByAlphabet(): List<User> =
        this.sortedBy { it.lastName; it.firstName }

    private fun List<User>.sortedByBirthdate(): List<User> = this.sortedBy {
        it.birthday.dayOfMonth; it.birthday.month
    }

    private fun LocalDate.beforeNowDay(): Boolean {
        val nowMonth = LocalDate.now().month
        val nowDay = LocalDate.now().dayOfMonth
        return nowMonth > this.month || (nowMonth == this.month && nowDay > this.dayOfMonth)
    }

}