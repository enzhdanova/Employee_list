package com.example.task.mainactivity.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.mainactivity.ui.EmployeesUseCase
import com.example.task.mainactivity.utils.Department
import com.example.task.mainactivity.utils.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val employeesUseCase: EmployeesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(EmployeesUIState())
    val uiState: LiveData<EmployeesUIState> = _uiState

    init {
        fetchEmployees()
    }

    fun getCurrentEmployees() {
        employeesUseCase.getCurrentEmployeeList(
            department = uiState.value?.department ?: Department.ALL,
            sortType = uiState.value?.sortType ?: SortType.ALPHABET,
            filterString = uiState.value?.filter ?: ""
        ).onSuccess {
            _uiState.value = _uiState.value?.copy(employeeList = it)
        }.onFailure {
            _uiState.value = _uiState.value?.copy(error = true)
        }
    }

    fun fetchEmployees() {
        viewModelScope.launch {
            employeesUseCase.fetchEmployees().onSuccess {
                getCurrentEmployees()
            }.onFailure {
                _uiState.value = _uiState.value?.copy(error = true)
            }
        }
    }

    fun changeSortType(sortType: SortType) {
        _uiState.value = _uiState.value?.copy(sortType = sortType)
        getCurrentEmployees()
    }

    fun changeDepartment(department: Department) {
        _uiState.value = _uiState.value?.copy(department = department)
        getCurrentEmployees()
    }

    fun setFilter(filterString: String) {
        _uiState.value = _uiState.value?.copy(filter = filterString)
        getCurrentEmployees()
    }
}