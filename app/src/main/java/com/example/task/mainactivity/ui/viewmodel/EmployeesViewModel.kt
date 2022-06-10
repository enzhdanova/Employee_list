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

    private val _uiState = MutableLiveData<EmployeesUIState>()
    val uiState: LiveData<EmployeesUIState> = _uiState

    init {
        _uiState.value = EmployeesUIState()
    }

    fun getUserFromDepartment() {
        viewModelScope.launch {
            val users = employeesUseCase.getEmployeeList(
                departments = uiState.value?.departments ?: Departments.ALL,
                sortType = uiState.value?.sortType ?: SortType.ALPHABET,
                filterString = uiState.value?.filter ?: ""
            )

            users.onSuccess {
                println("MyApp: on success")
                _uiState.value = _uiState.value?.copy(employeeList = it, needUpdateList = false)
            }.onFailure {
                println("MyApp: on Failure")
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