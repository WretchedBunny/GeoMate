package com.example.geomate.ui.screens.signup

import android.net.Uri
import com.example.geomate.service.account.Authentication
import com.example.geomate.service.storage.StorageServiceMock
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModelMock() : SignUpViewModel {
    override val storageService = StorageServiceMock()
    override val uiState = MutableStateFlow(SignUpUiState()).asStateFlow()
    override fun updateEmail(email: String) {}
    override fun updatePassword(password: String) {}
    override fun updateFirstName(firstName: String) {}
    override fun updateLastName(lastName: String) {}
    override fun updateUsername(username: String) {}
    override fun updateBio(bio: String) {}
    override fun updateProfilePictureUri(profilePictureUri: Uri?) {}
    override fun updateIsFirstNameValid(isFirstNameValid: Boolean) {}
    override fun updateIsLastNameValid(isLastNameValid: Boolean) {}
    override fun updateIsUsernameValid(isUsernameValid: Boolean) {}
    override fun updateIsEmailValid(isEmailValid: Boolean) {}
    override fun updateIsPasswordValid(isPasswordValid: Boolean) {}
    override fun onSignUpClick(authentication: Authentication): Boolean {
        return true
    }

    override fun onGoogleClick(authentication: Authentication, authCredential: SignInCredential) {}
    override fun onFacebookClick() {}
    override fun onTwitterClick() {}
}