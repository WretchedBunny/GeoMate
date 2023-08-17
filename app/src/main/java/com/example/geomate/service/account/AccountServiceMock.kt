package com.example.geomate.service.account

import android.content.Intent
import android.content.IntentSender

class AccountServiceMock : AccountService {
    override suspend fun signIn(email: String, password: String) {}
    override suspend fun signUp(email: String, password: String) {}
    override suspend fun sendRecoveryEmail(email: String) {}
    override suspend fun googleSignIn(): IntentSender? {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithIntent(intent: Intent) {
        TODO("Not yet implemented")
    }
}