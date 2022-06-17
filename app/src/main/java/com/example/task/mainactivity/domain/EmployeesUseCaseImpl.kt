package com.example.task.mainactivity.domain

import com.example.task.mainactivity.data.model.Employees
import com.example.task.mainactivity.domain.entity.UIModel
import com.example.task.mainactivity.ui.EmployeesUseCase
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

    private fun List<Employees>.getSortList(sortType: SortType): List<UIModel> =
        when (sortType) {
            SortType.ALPHABET -> {
                sortedByAlphabet()
            }
            SortType.DATE_BIRTHDATE -> {
                getSortForNowDay()
            }
        }


    private fun List<Employees>.getSortForNowDay(): List<UIModel> {
        val sortUser = sortedByBirthdate()

        val usersBeforeNowDay = sortUser.takeWhile {
            it.birthday.beforeNowDay()
        }

        val usersAfterNowDay = sortUser.takeLast(sortUser.size - usersBeforeNowDay.size)

        val result = mutableListOf<UIModel>()
        result.addAll(usersAfterNowDay.getUIModel {
            UIModel.UserWithBirthday.toUserWithBirthday(it)
        })

        if (usersBeforeNowDay.isNotEmpty()) {
            result.add(UIModel.Separator())
            result.addAll(usersBeforeNowDay.getUIModel {
                UIModel.UserWithBirthday.toUserWithBirthday(
                    it
                )
            })
        }

        return result
    }


    private fun List<Employees>.getUIModel(toUiModel: (Employees) -> UIModel): List<UIModel> =
        map { user ->
            toUiModel(user)
        }

    private fun List<Employees>.sortedByAlphabet(): List<UIModel> =
        sortedBy { it.lastName; it.firstName }.getUIModel {
            UIModel.User.toUser(it)
        }

    private fun List<Employees>.sortedByBirthdate(): List<Employees> = sortedBy {
        it.birthday.dayOfMonth; it.birthday.month
    }

    private fun LocalDate.beforeNowDay(): Boolean {
        val nowMonth = LocalDate.now().month
        val nowDay = LocalDate.now().dayOfMonth
        return nowMonth > month || (nowMonth == month && nowDay > dayOfMonth)
    }

    private fun List<Employees>.getUsersFromDepartment(departments: Departments): List<Employees> {
        return if (departments == Departments.ALL) {
            this
        } else {
            this.filter {
                it.department == departments.name.lowercase()
            }
        }
    }
}