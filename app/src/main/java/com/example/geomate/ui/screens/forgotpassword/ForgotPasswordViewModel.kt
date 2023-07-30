package com.example.geomate.ui.screens.forgotpassword

import androidx.lifecycle.viewModelScope
import com.example.geomate.service.AccountService
import com.example.geomate.ui.screens.GeoMateViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel() : GeoMateViewModel() {
    private val accountService = AccountService(FirebaseAuth.getInstance())
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    private val email
        get() = uiState.value.email

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onResetClick() {
        viewModelScope.launch {
            launchCatching(block = { accountService.sendRecoveryEmail(email) })
        }
    }
}