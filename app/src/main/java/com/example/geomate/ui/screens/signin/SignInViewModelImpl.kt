package com.example.geomate.ui.screens.signin

import androidx.lifecycle.ViewModel
import com.example.geomate.service.account.Authentication
import com.example.geomate.service.bucket.BucketService
import com.example.geomate.service.storage.StorageService
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SignInViewModelImpl(
    override val storageService: StorageService,
    override val bucketService: BucketService,
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

    override suspend fun signIn(authentication: Authentication): FirebaseUser? {
        return authentication.signIn()
    }
}