package com.example.task.mainactivity.domain

import com.example.task.mainactivity.data.model.Employee
import com.example.task.mainactivity.domain.entity.EmployeeItem
import com.example.task.mainactivity.ui.ProfileUseCase
import javax.inject.Inject

class ProfileUseCaseImpl @Inject constructor(
    private val employeesRepository: EmployeesRepository
) : ProfileUseCase {

    private suspend fun getEmployees(): Result<List<Employee>> {
        employeesRepository.getEmployees().onSuccess { result ->
            return Result.success(result)
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Exception())
    }

    override suspend fun getEmployee(id: String): Result<EmployeeItem> {

        getEmployees().onSuccess { employees ->
            val resultEmployee: Employee? = employees.find { employee ->
                employee.id == id
            }

            if (resultEmployee != null) {
                return Result.success(EmployeeItem.toUIModel(resultEmployee))
            }
        }

        return Result.failure(Exception())
    }
}