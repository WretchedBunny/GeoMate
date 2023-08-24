package com.example.geomate.service.account

import android.content.Intent
import com.example.geomate.service.storage.StorageService
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential

interface AccountService {
    val isUserAuthenticatedInFirebase: Boolean
    val storageService: StorageService

    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun getBeginSignInResult(): BeginSignInResult
    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Boolean
    fun getGoogleCredentialsWithIntent(intent: Intent?): String
}