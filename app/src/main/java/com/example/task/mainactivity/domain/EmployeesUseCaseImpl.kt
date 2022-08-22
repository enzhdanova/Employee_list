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

    private var employeesResult: Result<List<Employee>>? = null

    override suspend fun getCurrentEmployeeList(
        departments: Departments,
        sortType: SortType,
        filterString: String
    ): Result<List<UIModel>> {
        val resultFromRepository = employeesRepository.getEmployees()//employeesResult

        resultFromRepository.onFailure {
            return Result.failure(it)
        }.onSuccess { employeesResult ->
            val employees: List<UIModel> = employeesResult.getEmployeesFromDepartment(departments)
                .filter { employee ->
                    isContainsValue(employee, filterString)
                }.getSortedEmployees(sortType).toUIModelRelativelySortType(sortType)

            return checkIsEmptyAndGetResult(employees)
        }

        return Result.failure(Exception())
    }

    override suspend fun fetchEmployees(
        departments: Departments,
        sortType: SortType,
        filterString: String
    ) {
        employeesResult = employeesRepository.getEmployees()
    }

    private fun isContainsValue(employees: Employee, filterString: String): Boolean =
        employees.lastName.contains(filterString, true)
                || employees.firstName.contains(filterString, true)
                || employees.userTag.contains(filterString, true)


    private fun checkIsEmptyAndGetResult(employees: List<UIModel>) = if (employees.isEmpty()) {
        Result.success(listOf(UIModel.NotFound))
    } else {
        Result.success(employees)
    }
}