package com.example.task.mainactivity.domain

import com.example.task.mainactivity.data.model.Employee
import com.example.task.mainactivity.domain.entity.UIModel
import com.example.task.mainactivity.utils.Department
import com.example.task.mainactivity.utils.SortType
import java.time.LocalDate

fun List<Employee>.filter(department: Department, searchString: String): List<Employee> {

    if (department == Department.ALL && searchString == "") {
        return this
    }

    return if (department == Department.ALL) {
        this.filter { employee -> hasValue(employee, searchString) }
    } else {
        this.filter { employee ->
            employee.department == department.name.lowercase() && hasValue(employee, searchString)
        }
    }
}

private fun hasValue(employees: Employee, filterString: String): Boolean =
    employees.lastName.contains(filterString, true)
            || employees.firstName.contains(filterString, true)
            || employees.userTag.contains(filterString, true)


fun List<Employee>.getSortedEmployees(sortType: SortType): List<Employee> {
    return when (sortType) {
        SortType.ALPHABET -> {
            sortedByAlphabet()
        }
        SortType.DATE_BIRTHDATE -> {
            getSortRelativelyNowDay()
        }
    }
}

private fun List<Employee>.sortedByAlphabet(): List<Employee> =
    sortedBy { it.lastName; it.firstName }

private fun List<Employee>.getSortRelativelyNowDay(): List<Employee> {
    val sortEmployees = sortedByBirthdate()
    val employeesBeforeNowDay = sortEmployees.takeWhile {
        it.birthday.beforeNowDay()
    }
    val employeesAfterNowDay =
        sortEmployees.takeLast(sortEmployees.size - employeesBeforeNowDay.size)

    val resultSortedEmployees = mutableListOf<Employee>()
    resultSortedEmployees.addAll(employeesAfterNowDay)
    resultSortedEmployees.addAll(employeesBeforeNowDay)

    return resultSortedEmployees
}

private fun List<Employee>.sortedByBirthdate(): List<Employee> = sortedBy {
    it.birthday.dayOfMonth; it.birthday.month
}

private fun LocalDate.beforeNowDay(): Boolean {
    val nowMonth = LocalDate.now().month
    val nowDay = LocalDate.now().dayOfMonth
    return nowMonth > month || (nowMonth == month && nowDay > dayOfMonth)
}

fun List<Employee>.toUIModelRelativelySortType(sortType: SortType): List<UIModel> {
    return when (sortType) {
        SortType.ALPHABET -> {
            this.map { employee ->
                UIModel.EmployeeUI.toUIModel(employee)
            }
        }
        SortType.DATE_BIRTHDATE -> {
            val indexSeparator = getIndexSeparator(this)
            getUIModelWithSeparator(indexSeparator, this)
        }
    }
}

private fun getUIModelWithSeparator(indexSeparator: Int, employees: List<Employee>): List<UIModel> {
    val result: MutableList<UIModel> = employees.map { employee ->
        UIModel.EmployeeUIWithBirthday.toUIModel(employee)
    }.toMutableList()

    if (indexSeparator >= 0) {
        result.add(indexSeparator, UIModel.Separator())
    }
    return result
}

private fun getIndexSeparator(employees: List<Employee>): Int = employees
    .indexOfFirst { employee ->
        LocalDate.now().monthValue > employee.birthday.monthValue
    }
