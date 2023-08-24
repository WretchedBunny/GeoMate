package com.example.geomate.ui.screens.signin

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.geomate.ext.isEmailValid
import com.example.geomate.model.User
import com.example.geomate.service.account.AccountService
import com.example.geomate.service.account.Authentication
import com.example.geomate.service.storage.StorageService
import com.example.geomate.ui.screens.GeoMateViewModel
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SignInViewModelImpl(
    override val accountService: AccountService, override val storageService: StorageService,
) : GeoMateViewModel(), SignInViewModel {
    private val _uiState = MutableStateFlow(SignInUiState())
    override val uiState = _uiState.asStateFlow()

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

    override fun onGoogleClick(authentication: Authentication, signInCredential: SignInCredential) {
        val authCredential = GoogleAuthProvider.getCredential(signInCredential.googleIdToken, null)
        viewModelScope.launch {
            try {
                val loggedInUser = authentication.signIn(authCredential)
                if (loggedInUser != null) {
                    storageService.addUser(
                        User(
                            email = loggedInUser.email ?: ""
                        )
                    )
                }
            } catch (e: Exception) {
                Log.d("SignInViewModelImpl", e.message.toString())
            }
        }
    }

    override fun onFacebookClick() {
        TODO("Not yet implemented")
    }

    override fun onTwitterClick() {
        TODO("Not yet implemented")
    }

}