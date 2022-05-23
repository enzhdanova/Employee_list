package com.example.task.mainactivity.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.mainactivity.domain.EmployeesUseCase
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType

class EmployeesViewModel(
    private val employeesUseCase: EmployeesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<EmployeesUIState>()
    val uiState: LiveData<EmployeesUIState> = _uiState

    init {
        _uiState.value = EmployeesUIState()
    }

    fun getUserFromDepartment() {
        val users = employeesUseCase.getEmployeeList(
            departments = uiState.value?.departments ?: Departments.ALL,
            sortType = uiState.value?.sortType ?: SortType.ALPHABET,
            filterString = uiState.value?.filter ?: ""
        )

        _uiState.value = _uiState.value?.copy(employeeList = users, needUpdateList = false)
    }

    fun changeSortType(sortType: SortType) {
        _uiState.value = _uiState.value?.copy(sortType = sortType, needUpdateList = true)
    }

    fun changeDepartment(departments: Departments) {
        _uiState.value = _uiState.value?.copy(departments = departments, needUpdateList = true)
    }

    fun setFilter(filterString: String){
        _uiState.value = _uiState.value?.copy(filter = filterString, needUpdateList = true)
    }

}