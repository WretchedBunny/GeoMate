package com.example.geomate.service.account

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class FirebaseAccountService(
    private val auth: FirebaseAuth,
    val oneTapClient: SignInClient,
) : AccountService {
    private val web_client_id =
        "938799297885-9pradotv25k0sqvod236q4p0ng8u6p8e.apps.googleusercontent.com"

    private val signInRequest by lazy {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(web_client_id)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun googleSignIn(): IntentSender? {
        val result = try {
            Log.d("FirebaseAccountService", "AAAAAAAAAAAA")

            oneTapClient.beginSignIn(
                signInRequest
            ).await()
        } catch (e: Exception) {
            Log.d("FirebaseAccountService", "FFFFFFFFFFFFFFFFFFFFFFF")
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        Log.d("FirebaseAccountService", "${result?.pendingIntent?.intentSender == null}")
        return result?.pendingIntent?.intentSender
    }

    override suspend fun signInWithIntent(intent: Intent) {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            /*
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
             */
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e else TODO()
            /*
            SignInResult(
                data = null,
                errorMessage = e.message
            )
             */
        }
    }

}