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
        getUserFromDepartment()
    }

    fun getUserFromDepartment() {
        val users = employeesUseCase.getEmploeeList(
            uiState.value?.departments ?: Departments.ALL,
            uiState.value?.sortType ?: SortType.ALPHABET
        )

        _uiState.value = _uiState.value?.copy(employeeList = users)

    }

    fun changeSortType(sortType: SortType) {
        _uiState.value = _uiState.value?.copy(sortType = sortType)
    }

    fun changeDepartment(departments: Departments) {
        _uiState.value = _uiState.value?.copy(departments = departments)
    }

}