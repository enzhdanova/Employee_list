package com.example.task.mainactivity.domain

import com.example.task.mainactivity.data.model.Employee
import com.example.task.mainactivity.domain.entity.UIModel
import com.example.task.mainactivity.ui.EmployeesUseCase
import com.example.task.mainactivity.utils.Department
import com.example.task.mainactivity.utils.SortType
import javax.inject.Inject

class EmployeesUseCaseImpl @Inject constructor(
    private val employeesRepository: EmployeesRepository
) : EmployeesUseCase {

    private val employees: MutableList<Employee> = mutableListOf()

    override fun getCurrentEmployeeList(
        department: Department,
        sortType: SortType,
        filterString: String
    ): Result<List<UIModel>> {
        val copyEmployees = employees

        val employeesUI: List<UIModel> = copyEmployees.filter(department, filterString)
            .getSortedEmployees(sortType)
            .toUIModelRelativelySortType(sortType)

        return checkIsEmptyAndGetResult(employeesUI)
    }

    override suspend fun fetchEmployees(): Result<Boolean> {
        employeesRepository.getEmployees().onSuccess { result ->
            employees.clear()
            employees.addAll(result)
            return Result.success(true)
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Exception())
    }

    private fun checkIsEmptyAndGetResult(employees: List<UIModel>) = if (employees.isEmpty()) {
        Result.success(listOf(UIModel.NotFound))
    } else {
        Result.success(employees)
    }
}