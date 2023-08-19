package com.example.geomate.ui.screens.signin

import com.example.geomate.model.Response
import com.example.geomate.service.account.AccountServiceMock
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModelMock : SignInViewModel {
    override val accountService = AccountServiceMock()
    override val uiState = MutableStateFlow(SignInUiState()).asStateFlow()
    override var oneTapSignInResponse: Response<BeginSignInResult>
        get() = TODO("Not yet implemented")
        set(value) {}
    override var signInWithGoogleResponse: Response<Boolean>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun updateEmail(email: String) {}
    override fun updatePassword(password: String) {}
    override fun updateIsEmailValid(isEmailValid: Boolean) {}
    override fun updateIsPasswordValid(isPasswordValid: Boolean) {}
    override fun onSignInClick(): Boolean = true
    override fun onFacebookClick() {}
    override suspend fun onGoogleClick() {}
    override fun onTwitterClick() {}
    override fun signInWithGoogle(googleCredential: AuthCredential) {
        TODO("Not yet implemented")
    }
}