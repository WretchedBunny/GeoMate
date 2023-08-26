package com.example.geomate.ui.screens.signin

import com.example.geomate.service.account.Authentication
import com.example.geomate.service.storage.StorageService
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow

interface SignInViewModel {
    val uiState: StateFlow<SignInUiState>
    val storageService: StorageService
    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun updateIsEmailValid(isEmailValid: Boolean)
    fun updateIsPasswordValid(isPasswordValid: Boolean)
    suspend fun signIn(authentication: Authentication): FirebaseUser?
}