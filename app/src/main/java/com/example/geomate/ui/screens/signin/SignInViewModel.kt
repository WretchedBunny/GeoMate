package com.example.geomate.ui.screens.signin

import com.example.geomate.model.Response
import com.example.geomate.service.account.AccountService
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.StateFlow

interface SignInViewModel {
    val accountService: AccountService
    val uiState: StateFlow<SignInUiState>
    var oneTapSignInResponse: Response<BeginSignInResult>
    var signInWithGoogleResponse: Response<Boolean>
    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun updateIsEmailValid(isEmailValid: Boolean)
    fun updateIsPasswordValid(isPasswordValid: Boolean)
    fun onSignInClick(): Boolean
    fun onFacebookClick()
    fun onTwitterClick()
    suspend fun onGoogleClick()
    fun signInWithGoogle(googleCredential: AuthCredential)
}