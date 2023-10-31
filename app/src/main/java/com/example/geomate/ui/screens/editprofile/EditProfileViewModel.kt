package com.example.geomate.ui.screens.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    fun fetchUser(userId: String) = viewModelScope.launch {
        usersRepository.getSingleAsFlow(userId).collect { userOrNull ->
            userOrNull?.let { user ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        firstName = user.firstName,
                        isFirstNameValid = true,
                        lastName = user.lastName,
                        isLastNameValid = true,
                        username = user.username,
                        isUsernameValid = true,
                        email = user.email,
                        isEmailValid = true,
                        bio = user.bio,
                    )
                }
            }
        }
    }

    fun updateUser(userId: String) = viewModelScope.launch {
        val updates = mapOf(
            "firstName" to uiState.value.firstName,
            "lastName" to uiState.value.lastName,
            "username" to uiState.value.username,
            "email" to uiState.value.email,
            "bio" to uiState.value.bio,
        )
        usersRepository.update(userId, updates)
    }

    fun updateFirstName(firstName: String) {
        _uiState.update { it.copy(firstName = firstName) }
    }

    fun updateIsFirstNameValid(isFirstNameValid: Boolean) {
        _uiState.update { it.copy(isFirstNameValid = isFirstNameValid) }
    }

    fun updateLastName(lastName: String) {
        _uiState.update { it.copy(lastName = lastName) }
    }

    fun updateIsLastNameValid(isLastNameValid: Boolean) {
        _uiState.update { it.copy(isLastNameValid =  isLastNameValid) }
    }

    fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun updateIsUsernameValid(isUsernameValid: Boolean) {
        _uiState.update { it.copy(isUsernameValid = isUsernameValid) }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updateIsEmailValid(isEmailValid: Boolean) {
        _uiState.update { it.copy(isEmailValid = isEmailValid) }
    }

    fun updateBio(bio: String) {
        _uiState.update { it.copy(bio = bio) }
    }
}