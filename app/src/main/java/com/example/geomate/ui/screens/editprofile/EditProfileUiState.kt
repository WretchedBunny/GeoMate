package com.example.geomate.ui.screens.editprofile

data class EditProfileUiState(
    val isLoading: Boolean = true,

    val firstName: String = "",
    val isFirstNameValid: Boolean = true,

    val lastName: String = "",
    val isLastNameValid: Boolean = true,

    val username: String = "",
    val isUsernameValid: Boolean = true,

    val email: String = "",
    val isEmailValid: Boolean = true,

    val bio: String = "",
)
