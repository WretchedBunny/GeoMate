package com.example.geomate.service.account

import android.content.Intent
import com.example.geomate.model.Response
import com.example.geomate.service.storage.StorageService
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential

class AccountServiceMock(
) : AccountService {
    override val isUserAuthenticatedInFirebase: Boolean
        get() = TODO("Not yet implemented")
    override val storageService: StorageService
        get() = TODO("Not yet implemented")

    override suspend fun signIn(email: String, password: String) {}
    override suspend fun signUp(email: String, password: String) {}
    override suspend fun sendRecoveryEmail(email: String) {}
    override suspend fun getBeginSignInResult(): Response<BeginSignInResult> {
        TODO("Not yet implemented")
    }

    override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Response<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getGoogleCredentialsWithIntent(intent: Intent?): Response<String> {
        TODO("Not yet implemented")
    }

}