package com.example.geomate.ui.screens.forgotpassword

import com.example.geomate.service.account.AccountServiceMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForgotPasswordViewModelMock : ForgotPasswordViewModel {
    override val accountService = AccountServiceMock()
    override val uiState = MutableStateFlow(ForgotPasswordUiState()).asStateFlow()
    override fun updateEmail(email: String) {}
    override fun updateIsEmailValid(isEmailValid: Boolean) {}
    override fun onResetClick() {}
}