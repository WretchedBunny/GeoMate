package com.example.geomate.ui.screens.forgotpassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.service.account.AccountService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModelImpl(
    override val accountService: AccountService,
) : ViewModel(), ForgotPasswordViewModel {
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    override val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    override fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    override fun updateIsEmailValid(isEmailValid: Boolean) {
        _uiState.update { it.copy(isEmailValid = isEmailValid) }
    }

    override fun onResetClick() {
        viewModelScope.launch {
            try {
                accountService.sendRecoveryEmail(uiState.value.email)
            } catch (e: Exception) {
                Log.d("Exception", e.message.toString())
            }
        }
    }
}