package com.example.geomate.ui.screens.signin

import com.example.geomate.ext.isEmailValid
import com.example.geomate.service.account.AccountService
import com.example.geomate.ui.screens.GeoMateViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SignInViewModelImpl(
    override val accountService: AccountService,
) : GeoMateViewModel(), SignInViewModel {
    private val _uiState = MutableStateFlow(SignInUiState())
    override val uiState = _uiState.asStateFlow()

    override fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    override fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    override fun onSignInClick(): Boolean {
        if (!uiState.value.email.isEmailValid()) {
            return false
        }
        launchCatching {
            accountService.signIn(uiState.value.email, uiState.value.password)
        }
        return FirebaseAuth.getInstance().currentUser != null
    }

    override fun onFacebookClick() {
        TODO("Not yet implemented")
    }

    override fun onGoogleClick() {
        TODO("Not yet implemented")
    }

    override fun onTwitterClick() {
        TODO("Not yet implemented")
    }


}