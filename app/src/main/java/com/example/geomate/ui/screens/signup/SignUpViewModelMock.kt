package com.example.geomate.ui.screens.signup

import android.net.Uri
import com.example.geomate.service.account.AccountService
import com.example.geomate.service.account.AccountServiceMock
import com.example.geomate.service.storage.StorageServiceMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModelMock() : SignUpViewModel {
    override val accountService: AccountService = AccountServiceMock()
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
    override fun onSignUpClick(): Boolean = true
    override fun onFacebookClick() {}
    override fun onGoogleClick() {}
    override fun onTwitterClick() {}
}