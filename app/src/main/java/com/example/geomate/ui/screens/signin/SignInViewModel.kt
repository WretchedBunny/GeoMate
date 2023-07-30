package com.example.geomate.ui.screens.signin

import com.example.geomate.ext.isEmailValid
import com.example.geomate.service.AccountService
import com.example.geomate.ui.screens.GeoMateViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SignInViewModel : GeoMateViewModel() {
    private val accountService = AccountService(FirebaseAuth.getInstance())
    private var _uiState = MutableStateFlow(SignInUIState())
    val uiState = _uiState.asStateFlow()

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onSignInClick(): Boolean {
        if (!email.isEmailValid()) {
            return false
        }
        launchCatching(block = {
            accountService.signIn(email, password)
        })
        return FirebaseAuth.getInstance().currentUser != null
    }
}