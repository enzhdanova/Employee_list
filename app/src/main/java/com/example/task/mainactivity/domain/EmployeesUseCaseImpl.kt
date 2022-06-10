package com.example.task.mainactivity.domain

import com.example.task.mainactivity.data.model.Employee
import com.example.task.mainactivity.ui.EmployeesUseCase
import com.example.task.mainactivity.ui.model.UIModel
import com.example.task.mainactivity.ui.model.UserItem
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

class EmployeesUseCaseImpl @Inject constructor(
    private val employeesRepository: EmployeesRepository
) : EmployeesUseCase {

    override suspend fun getEmployeeList(
        departments: Departments,
        sortType: SortType,
        filterString: String
    ): Result<List<UIModel>> {
        val resultFromRepository = employeesRepository.getUsers()

        resultFromRepository.onFailure {
            return Result.failure(it)
        }.onSuccess { usersList ->
            val users: List<UIModel> = usersList.getUsersFromDepartment(departments)
                .filter { user ->
                    user.lastName.contains(filterString, true)
                            || user.firstName.contains(filterString, true)
                            || user.userTag.contains(filterString, true)
                }.getSortList(sortType)

            return if (users.isEmpty())
                Result.success(listOf(UIModel.NotFound))
            else
                Result.success(users)
        }

        return Result.failure(Exception())
    }

    private fun List<Employee>.getSortList(sortType: SortType): List<UIModel> =
        when (sortType) {
            SortType.ALPHABET -> {
                sortedByAlphabet().getUIModelForUser()
            }
            SortType.DATE_BIRTHDATE -> {
                getSortForNowDay()
            }
        }


    private fun List<Employee>.getSortForNowDay(): List<UIModel> {
        val sortUser = sortedByBirthdate()

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

    private fun List<Employee>.getUIModelForUser(): List<UIModel> =
        this.map { user ->
            val item = UserItem.toUIModel(user)
            UIModel.User(item)
        }


    private fun List<Employee>.getUIModelForUserWithBd(): List<UIModel> =
        map { user ->
            val item = UserItem.toUIModel(user)
            UIModel.UserWithBirthday(item)
        }

    private fun List<Employee>.sortedByAlphabet(): List<Employee> =
        sortedBy { it.lastName; it.firstName }

    private fun List<Employee>.sortedByBirthdate(): List<Employee> = sortedBy {
        it.birthday.dayOfMonth; it.birthday.month
    }

    private fun LocalDate.beforeNowDay(): Boolean {
        val nowMonth = LocalDate.now().month
        val nowDay = LocalDate.now().dayOfMonth
        return nowMonth > month || (nowMonth == month && nowDay > dayOfMonth)
    }

    private fun List<Employee>.getUsersFromDepartment(departments: Departments): List<Employee> {
        return if (departments == Departments.ALL) {
            this
        } else {
            this.filter {
                it.department == departments.name.lowercase()
            }
        }
    }
}