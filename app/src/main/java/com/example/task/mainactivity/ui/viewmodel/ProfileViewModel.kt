package com.example.task.mainactivity.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.mainactivity.ui.EmployeesUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val employeesUseCase: EmployeesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(ProfileUIState())
    val uiState: LiveData<ProfileUIState> = _uiState

    fun getEmployee(id: String) {
        viewModelScope.launch {
            employeesUseCase.getEmployee(id)
        }
    }
}