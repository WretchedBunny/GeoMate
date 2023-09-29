package com.example.geomate.ui.screens.signin

import androidx.lifecycle.ViewModel
import com.example.geomate.authentication.Authentication
import com.example.geomate.data.repositories.UsersRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SignInViewModel(val usersRepository: UsersRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun updateIsEmailValid(isEmailValid: Boolean) {
        _uiState.update { it.copy(isEmailValid = isEmailValid) }
    }

    fun updateIsPasswordValid(isPasswordValid: Boolean) {
        _uiState.update { it.copy(isPasswordValid = isPasswordValid) }
    }

    suspend fun signIn(authentication: Authentication): FirebaseUser? {
        return authentication.signIn()
    }
}