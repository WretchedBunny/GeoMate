package com.example.geomate.ui.screens.signup

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.geomate.model.User
import com.example.geomate.service.account.AccountService
import com.example.geomate.service.account.Authentication
import com.example.geomate.service.storage.StorageService
import com.example.geomate.ui.screens.GeoMateViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModelImpl(
    override val storageService: StorageService, override val accountService: AccountService,
) : GeoMateViewModel(), SignUpViewModel {
    private val _uiState = MutableStateFlow(SignUpUiState())
    override val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    override fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    override fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    override fun updateFirstName(firstName: String) {
        _uiState.update { it.copy(firstName = firstName) }
    }

    override fun updateLastName(lastName: String) {
        _uiState.update { it.copy(lastName = lastName) }
    }

    override fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    override fun updateBio(bio: String) {
        _uiState.update { it.copy(bio = bio) }
    }

    override fun updateProfilePictureUri(profilePictureUri: Uri?) {
        _uiState.update { it.copy(profilePictureUri = profilePictureUri) }
    }

    override fun updateIsFirstNameValid(isFirstNameValid: Boolean) {
        _uiState.update { it.copy(isFirstNameValid = isFirstNameValid) }
    }

    override fun updateIsLastNameValid(isLastNameValid: Boolean) {
        _uiState.update { it.copy(isLastNameValid = isLastNameValid) }
    }

    override fun updateIsUsernameValid(isUsernameValid: Boolean) {
        _uiState.update { it.copy(isUsernameValid = isUsernameValid) }
    }

    override fun updateIsEmailValid(isEmailValid: Boolean) {
        _uiState.update { it.copy(isEmailValid = isEmailValid) }
    }

    override fun updateIsPasswordValid(isPasswordValid: Boolean) {
        _uiState.update { it.copy(isPasswordValid = isPasswordValid) }
    }

    override fun onSignUpClick(authentication: Authentication): Boolean {
        viewModelScope.launch {
            try {
                storageService.addUser(
                    User(
                        email = uiState.value.email,
                        username = uiState.value.username,
                        firstName = uiState.value.firstName,
                        lastName = uiState.value.lastName,
                        profilePictureUri = uiState.value.profilePictureUri,
                        bio = uiState.value.bio
                    )
                )
                authentication.signUp()
            } catch (e: Exception) {
                Log.d("Exception", e.message.toString())
            }
        }
        return authentication.user != null
    }

    override fun onFacebookClick() {
        TODO("Not yet implemented")
    }

    override fun onGoogleClick() {
        TODO("Not yet implemented")
    }

    override fun onTwitterClick() {
        TODO("Not yet implemented")
    }
}