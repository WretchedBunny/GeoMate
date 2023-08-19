package com.example.geomate.service.account

import android.content.Intent
import android.util.Log
import com.example.geomate.model.Response
import com.example.geomate.model.Response.Failure
import com.example.geomate.model.Response.Success
import com.example.geomate.model.User
import com.example.geomate.service.storage.StorageService
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAccountService(
    private val auth: FirebaseAuth,
    val oneTapClient: SignInClient,
    override val storageService: StorageService,
) : AccountService {

    override val isUserAuthenticatedInFirebase: Boolean = auth.currentUser != null

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

    override suspend fun getBeginSignInResult(): Response<BeginSignInResult> {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Log.d("FirebaseAccountService", "$signInResult")
            Success<BeginSignInResult>(signInResult)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential,
    ): Response<Boolean> {
        return try {
            Log.d("firebaseSignInWithGoogle", "FFFFFFFFFFFFf")
            val authResult = auth.signInWithCredential(googleCredential).await()
            Log.d("firebaseSignInWithGoogle", "$authResult") //???

            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                storageService.addUser(
                    User(
                        email = auth.currentUser?.email,
                        username = auth.currentUser?.uid.toString(),
                        firstName = auth.currentUser?.displayName,
                        lastName = auth.currentUser?.displayName,
                        profilePictureUri = auth.currentUser?.photoUrl,
                        bio = ""
                    )
                )
            }
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun getGoogleCredentialsWithIntent(intent: Intent?): Response<String> {
        return try {
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            return Success<String>(credential.googleIdToken)
        } catch (e: Exception) {
            Failure(e)
        }
    }


}