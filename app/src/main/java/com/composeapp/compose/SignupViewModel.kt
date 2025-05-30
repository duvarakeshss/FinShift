package com.composeapp.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    private val userModel = MainApplication.getUserModel()

    private val _signupState = MutableStateFlow<SignupState>(SignupState.Initial)
    val signupState = _signupState.asStateFlow()

    fun signUp(username: String, password: String) {
        viewModelScope.launch {
            try {
                _signupState.value = SignupState.Loading

                // Check if username already exists
                if (userModel.userDao.isUsernameExists(username)) {
                    _signupState.value = SignupState.Error("Username already exists")
                    return@launch
                }

                // Create new user
                userModel.createUser(username, password)
                _signupState.value = SignupState.Success
            } catch (e: Exception) {
                _signupState.value = SignupState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}

sealed class SignupState {
    object Initial : SignupState()
    object Loading : SignupState()
    object Success : SignupState()
    data class Error(val message: String) : SignupState()
}