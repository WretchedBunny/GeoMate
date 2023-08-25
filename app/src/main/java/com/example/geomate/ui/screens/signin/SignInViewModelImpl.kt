package com.example.geomate.ui.screens.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.ext.isEmailValid
import com.example.geomate.service.account.Authentication
import com.example.geomate.service.storage.StorageService
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SignInViewModelImpl(
    override val storageService: StorageService,
) : ViewModel(), SignInViewModel {
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

    override fun onSignInClick(authentication: Authentication): Boolean {
        if (!uiState.value.email.isEmailValid()) return false
        viewModelScope.launch {
            try {
                authentication.signIn()
            } catch (e: Exception) {
                Log.d("Exception", e.message.toString())
            }
        }
        return authentication.user != null
    }

    override fun onGoogleClick(authentication: Authentication, authCredential: SignInCredential) {
        viewModelScope.launch {
            try {
                authentication.signIn()
            } catch (e: Exception) {
                e.printStackTrace()
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