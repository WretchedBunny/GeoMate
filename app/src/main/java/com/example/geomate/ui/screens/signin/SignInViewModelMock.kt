package com.example.geomate.ui.screens.signin

import com.example.geomate.service.account.Authentication
import com.example.geomate.service.storage.StorageService
import com.example.geomate.service.storage.StorageServiceMock
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModelMock : SignInViewModel {
    override val uiState = MutableStateFlow(SignInUiState()).asStateFlow()
    override val storageService: StorageService = StorageServiceMock()

    override fun updateEmail(email: String) {}
    override fun updatePassword(password: String) {}
    override fun updateIsEmailValid(isEmailValid: Boolean) {}
    override fun updateIsPasswordValid(isPasswordValid: Boolean) {}
    override fun onSignInClick(authentication: Authentication): Boolean = true
    override fun onFacebookClick() {}
    override fun onTwitterClick() {}
    override fun onGoogleClick(authentication: Authentication, authCredential: SignInCredential) {}
}