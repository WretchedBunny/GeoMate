package com.example.geomate.ui.screens.signin

import com.example.geomate.service.account.AccountServiceMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModelMock : SignInViewModel {
    override val accountService = AccountServiceMock()
    override val uiState = MutableStateFlow(SignInUiState()).asStateFlow()
    override fun updateEmail(email: String) {}
    override fun updatePassword(password: String) {}
    override fun updateIsEmailValid(isEmailValid: Boolean) {}
    override fun updateIsPasswordValid(isPasswordValid: Boolean) {}
    override fun onSignInClick(): Boolean = true
    override fun onFacebookClick() {}
    override fun onGoogleClick() {}
    override fun onTwitterClick() {}
}