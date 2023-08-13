package com.example.geomate.ui.screens.forgotpassword

import com.example.geomate.service.account.AccountService
import com.example.geomate.ui.screens.GeoMateViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForgotPasswordViewModelImpl(
    override val accountService: AccountService,
) : GeoMateViewModel(), ForgotPasswordViewModel {
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    override val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    override fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    override fun updateIsEmailValid(isEmailValid: Boolean) {
        _uiState.update { it.copy(isEmailValid = isEmailValid) }
    }

    override fun onResetClick() {
        launchCatching {
            accountService.sendRecoveryEmail(uiState.value.email)
        }
    }
}