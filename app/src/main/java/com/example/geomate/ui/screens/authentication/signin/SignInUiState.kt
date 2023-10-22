package com.example.geomate.ui.screens.authentication.signin

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
)