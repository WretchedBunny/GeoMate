package com.example.geomate.service.account

import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class GoogleSignInAuthentication(
    override val auth: FirebaseAuth,
    val oneTapClient: SignInClient,
) :
    Authentication {
    override val user: FirebaseUser? = auth.currentUser

    private val signInRequest by lazy {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("938799297885-9pradotv25k0sqvod236q4p0ng8u6p8e.apps.googleusercontent.com")
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    suspend fun getIntentSender(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                signInRequest
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return result?.pendingIntent?.intentSender
    }

    override suspend fun signIn(authCredential: AuthCredential): FirebaseUser? {
        
        return null
    }

    override suspend fun signUp() {
        TODO("Not yet implemented")
    }

}