package com.example.geomate.service.account

import android.content.Intent
import android.content.IntentSender

interface AccountService {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun googleSignIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent)
}