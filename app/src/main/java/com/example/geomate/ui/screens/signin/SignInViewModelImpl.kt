package com.example.geomate.ui.screens.signin

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.geomate.ext.isEmailValid
import com.example.geomate.model.Response
import com.example.geomate.model.Response.Success
import com.example.geomate.service.account.AccountService
import com.example.geomate.service.account.Authentication
import com.example.geomate.ui.screens.GeoMateViewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SignInViewModelImpl(
    override val accountService: AccountService,
) : GeoMateViewModel(), SignInViewModel {
    private val _uiState = MutableStateFlow(SignInUiState())
    override val uiState = _uiState.asStateFlow()

    override var oneTapSignInResponse by mutableStateOf<Response<BeginSignInResult>>(Success(null))
    override var signInWithGoogleResponse by mutableStateOf<Response<Boolean>>(Success(false))

    override fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    override fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    override fun updateIsEmailValid(isEmailValid: Boolean) {
        _uiState.update { it.copy(isEmailValid = isEmailValid) }
    }

    override fun updateIsPasswordValid(isPasswordValid: Boolean) {
        _uiState.update { it.copy(isPasswordValid = isPasswordValid) }
    }

    /*
    override fun onSignInClick(): Boolean {
        if (!uiState.value.email.isEmailValid()) {
            return false
        }
        launchCatching {
            accountService.signIn(uiState.value.email, uiState.value.password)
        }
        return FirebaseAuth.getInstance().currentUser != null
    }
     */
    override fun onSignInClick(authentication: Authentication): Boolean {
        if (!uiState.value.email.isEmailValid()) return false
        viewModelScope.launch {
            try {
                authentication.signIn(
                    EmailAuthProvider.getCredential(
                        uiState.value.email,
                        uiState.value.password
                    )
                )
            } catch (e: Exception) {
                Log.d("Exception", e.message.toString())
            }
        }
        return authentication.user != null
    }

    override fun onFacebookClick() {
        TODO("Not yet implemented")
    }

    override fun onTwitterClick() {
        TODO("Not yet implemented")
    }

    override suspend fun onGoogleClick() {
        launchCatching {
            oneTapSignInResponse = accountService.getBeginSignInResult()
        }
    }

    override fun signInWithGoogle(googleCredential: AuthCredential) {
        launchCatching {
            signInWithGoogleResponse = accountService.firebaseSignInWithGoogle(googleCredential)
        }
    }
}