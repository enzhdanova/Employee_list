package com.example.task.mainactivity.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.mainactivity.data.MockeData
import com.example.task.mainactivity.data.User
import com.example.task.mainactivity.ui.EmployeesRepository
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType
import kotlinx.coroutines.launch

class EmployeesViewModel(
    private val employeesRepository: EmployeesRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<EmployeesUIState>()
    val uiState: LiveData<EmployeesUIState> = _uiState

    init {
        _uiState.value = EmployeesUIState()
        getUserFromDepartment()
    }

    fun getUserFromDepartment() {
        val users = employeesRepository.getUsersFromDepartment(
            _uiState.value?.departments ?: Departments.ALL
        )
        val result = when (_uiState.value?.sortType ?: SortType.ALPHABET) {
            SortType.ALPHABET -> employeesRepository.getSortedByAlphabetList(users)
            SortType.DATE_BIRTHDATE -> employeesRepository.getSortedByBDList(users)
        }

        _uiState.value = _uiState.value?.copy(employeeList = result)

    }

    fun changeSortType(sortType: SortType) {
        _uiState.value = _uiState.value?.copy(sortType = sortType)
    }

    fun changeDepartment(departments: Departments) {
        _uiState.value = _uiState.value?.copy(departments = departments)
    }

}