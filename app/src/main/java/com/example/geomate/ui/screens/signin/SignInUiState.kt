package com.example.geomate.ui.screens.signin

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
)