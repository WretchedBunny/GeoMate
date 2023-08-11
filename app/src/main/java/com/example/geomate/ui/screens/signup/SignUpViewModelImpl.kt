package com.example.geomate.ui.screens.signup

import android.net.Uri
import com.example.geomate.model.User
import com.example.geomate.service.account.FirebaseAccountService
import com.example.geomate.service.storage.StorageService
import com.example.geomate.ui.screens.GeoMateViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModelImpl(
    override val storageService: StorageService,
) : GeoMateViewModel(), SignUpViewModel {
    private val accountService = FirebaseAccountService(FirebaseAuth.getInstance())
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

    override fun onSignUpClick(): Boolean {
        launchCatching {
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
            accountService.signUp(uiState.value.email, uiState.value.password)
        }
        return FirebaseAuth.getInstance().currentUser != null
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