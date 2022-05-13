package com.example.task.mainactivity.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.mainactivity.data.MockeData
import com.example.task.mainactivity.ui.EmployeesRepository
import com.example.task.mainactivity.utils.Departments
import kotlinx.coroutines.launch

class EmployeesViewModel(
    val employeesRepository: EmployeesRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<EmployeesUIState>()
    val uiState: LiveData<EmployeesUIState> = _uiState

    init {
        _uiState.value = EmployeesUIState()
        getEmployees()
    }

    private fun getEmployees() {
        viewModelScope.launch {
            val users = employeesRepository.getAllUsers()
            _uiState.value = _uiState.value?.copy(employeeList = users)
        }
    }

    fun getUserFromDepartment(departments: Departments) =
        if (departments == Departments.ALL) {
            getEmployees()
        } else {
            val users = employeesRepository.getUsersFromDepartment(departments)
            _uiState.value = _uiState.value?.copy(employeeList = users)

        }

}