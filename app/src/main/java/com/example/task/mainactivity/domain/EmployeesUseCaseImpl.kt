package com.example.task.mainactivity.domain

import com.example.task.mainactivity.data.model.Employee
import com.example.task.mainactivity.domain.entity.UIModel
import com.example.task.mainactivity.ui.EmployeesUseCase
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType
import java.lang.Exception
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
            val users: List<UIModel> = usersList.getEmployeesFromDepartment(departments)
                .filter { user ->
                    isContainsValue(user, filterString)
                }.getSortedEmployees(sortType).toUIModel(sortType)

            return if (users.isEmpty())
                Result.success(listOf(UIModel.NotFound))
            else
                Result.success(users)
        }

        return Result.failure(Exception())
    }

    private fun isContainsValue(employees: Employee, filterString: String): Boolean =
        employees.lastName.contains(filterString, true)
                || employees.firstName.contains(filterString, true)
                || employees.userTag.contains(filterString, true)
}