package com.example.task.mainactivity.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.mainactivity.data.MockeData
import kotlinx.coroutines.launch

class EmployeesViewModel : ViewModel() {

    private val _uiState = MutableLiveData<EmployeesUIState>()
    val uiState: LiveData<EmployeesUIState> = _uiState

    init {
        println("MyApp: in init block:")
        println("MyApp: ${_uiState.value}")
        _uiState.value = EmployeesUIState()
        getEmployees()
        println("MyApp: after getEmploees:")
        println("MyApp: ${_uiState.value}")
    }

    fun getEmployees(){
        viewModelScope.launch {
            val users = MockeData.users
            _uiState.value = _uiState.value?.copy(employeeList = users)
        }
    }

}