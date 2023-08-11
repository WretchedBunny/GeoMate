package com.example.geomate.ui.screens.forgotpassword

import com.example.geomate.service.account.AccountService
import kotlinx.coroutines.flow.StateFlow

interface ForgotPasswordViewModel {
    val accountService: AccountService
    val uiState: StateFlow<ForgotPasswordUiState>
    fun updateEmail(email: String)
    fun onResetClick()
}