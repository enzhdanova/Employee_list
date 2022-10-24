package com.example.task.mainactivity.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.mainactivity.ui.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(ProfileUIState())
    val uiState: LiveData<ProfileUIState> = _uiState

    fun getEmployee(id: String) {
        viewModelScope.launch {
            val employeeResult = profileUseCase.getEmployee(id)

            employeeResult.onSuccess { employee ->
                _uiState.value = _uiState.value?.copy(employee = employee)
            }
        }
    }
}