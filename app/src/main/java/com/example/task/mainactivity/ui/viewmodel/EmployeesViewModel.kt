package com.example.task.mainactivity.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.mainactivity.ui.EmployeesUseCase
import com.example.task.mainactivity.utils.Departments
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
        val employees = employeesUseCase.getCurrentEmployeeList(
            departments = uiState.value?.departments ?: Departments.ALL,
            sortType = uiState.value?.sortType ?: SortType.ALPHABET,
            filterString = uiState.value?.filter ?: ""
        )

        employees.onSuccess {
            _uiState.value = _uiState.value?.copy(employeeList = it, needUpdateList = false)
        }.onFailure {
            _uiState.value = _uiState.value?.copy(error = true)
        }
    }

    fun fetchEmployees() {
        viewModelScope.launch {
            val fetchResult = employeesUseCase.fetchEmployees(
                departments = Departments.ALL,
                sortType = SortType.ALPHABET,
                filterString = ""
            )

            fetchResult.onSuccess {
                _uiState.value = _uiState.value?.copy(needUpdateList = true)
            }.onFailure {
                _uiState.value = _uiState.value?.copy(error = true)
            }
        }
    }

    fun changeSortType(sortType: SortType) {
        _uiState.value = _uiState.value?.copy(sortType = sortType, needUpdateList = true)
    }

    fun changeDepartment(departments: Departments) {
        _uiState.value = _uiState.value?.copy(departments = departments, needUpdateList = true)
    }

    fun setFilter(filterString: String) {
        _uiState.value = _uiState.value?.copy(filter = filterString, needUpdateList = true)
    }

}