package com.example.geomate.ui.screens.signin

import com.example.geomate.service.account.Authentication
import com.example.geomate.service.storage.StorageService
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.StateFlow

interface SignInViewModel {
    val uiState: StateFlow<SignInUiState>
    val storageService: StorageService
    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun updateIsEmailValid(isEmailValid: Boolean)
    fun updateIsPasswordValid(isPasswordValid: Boolean)
    fun onSignInClick(authentication: Authentication): Boolean
    fun onFacebookClick()
    fun onTwitterClick()
    fun onGoogleClick(authentication: Authentication, authCredential: SignInCredential)
}