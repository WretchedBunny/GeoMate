package com.example.geomate.ui.screens.signin

import com.example.geomate.service.account.AccountService
import kotlinx.coroutines.flow.StateFlow

interface SignInViewModel {
    val accountService: AccountService
    val uiState: StateFlow<SignInUiState>
    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun onSignInClick(): Boolean
    fun onFacebookClick()
    fun onGoogleClick()
    fun onTwitterClick()
}