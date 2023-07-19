package com.example.geomate.ui.screens.signup

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.geomate.ext.isEmailValid
import com.example.geomate.ext.isPasswordValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "SignUpViewModel"

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password
    private val firstName
        get() = uiState.value.firstName
    private val lastName
        get() = uiState.value.lastName
    private val username
        get() = uiState.value.username
    private val description
        get() = uiState.value.description

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun updateFirstName(firstName: String) {
        _uiState.update { it.copy(firstName = firstName) }
    }

    fun updateLastName(lastName: String) {
        _uiState.update { it.copy(lastName = lastName) }
    }

    fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun updateProfilePictureUri(profilePictureUri: Uri?) {
        _uiState.update { it.copy(profilePictureUri = profilePictureUri) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onContinueClick(pageNumber: Int): Boolean {
        when (pageNumber) {
            0 -> {
                val isEmailValid = email.isEmailValid()
                val isPasswordValid = password.isPasswordValid()

                if (!isEmailValid)
                /*TODO("Provide User with notification about email validation error.") */
                    Log.d(
                        TAG,
                        "Email for account creation isn't valid."
                    )
                if (!isPasswordValid)
                /*TODO("Provide User with notification about password validation error.") */
                    Log.d(
                        TAG,
                        "Password for account creation isn't valid."
                    )
                return isEmailValid && isPasswordValid
            }

            1 -> {
                val isNameValid = firstName.isNotBlank()
                val isSurnameValid = lastName.isNotBlank()
                val isUsernameValid = username.isNotBlank()

                if (!isNameValid) {
                    /*TODO("Provide User with notification about name validation error.") */
                    Log.d(
                        TAG,
                        "Name for account creation isn't valid."
                    )
                }
                if (!isSurnameValid) {
                    /*TODO("Provide User with notification about lastname validation error.") */
                    Log.d(
                        TAG,
                        "Lastname for account creation isn't valid."
                    )
                }
                if (!isUsernameValid) {
                    /*TODO("Provide User with notification about username validation error.") */
                    Log.d(
                        TAG,
                        "Username for account creation isn't valid."
                    )
                }

            }
        }
        return TODO("Provide the return value")
    }
}