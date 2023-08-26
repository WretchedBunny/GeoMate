package com.example.geomate.service.account

import android.content.IntentSender
import com.example.geomate.model.User
import com.example.geomate.service.storage.StorageService
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleSignInAuthentication(
    override val auth: FirebaseAuth,
    private val storageService: StorageService,
    private val authCredential: SignInCredential,

    ) : Authentication {

    override val user: FirebaseUser? = auth.currentUser

    companion object {
        private val requestBuilder by lazy {
            BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId("938799297885-9pradotv25k0sqvod236q4p0ng8u6p8e.apps.googleusercontent.com")
                        .setFilterByAuthorizedAccounts(true)
                        .build()
                )
        }

        suspend fun getSignInIntentSender(oneTapClient: SignInClient): IntentSender? {
            val result = try {
                oneTapClient.beginSignIn(
                    requestBuilder
                        .setAutoSelectEnabled(true)
                        .build()
                ).await()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            return result?.pendingIntent?.intentSender
        }

        suspend fun getSignUpIntentSender(oneTapClient: SignInClient): IntentSender? {
            val result = try {
                oneTapClient.beginSignIn(
                    requestBuilder
                        .setAutoSelectEnabled(false)
                        .build()
                ).await()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            return result?.pendingIntent?.intentSender
        }
    }

    override suspend fun signIn(): FirebaseUser? {
        val googleCredential = GoogleAuthProvider.getCredential(authCredential.googleIdToken, null)
        return try {
            val signInResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = signInResult.additionalUserInfo?.isNewUser ?: false
            val logedInUser = signInResult.user
            if (logedInUser != null && isNewUser) {
                logedInUser.uid.let { uid ->
                    storageService.addUser(
                        User(
                            uid = uid,
                            email = logedInUser.email ?: "",
                            username = uid.dropLast(8),
                            firstName = authCredential.givenName ?: "",
                            lastName = authCredential.familyName ?: "",
                            profilePictureUri = authCredential.profilePictureUri
                        )
                    )
                }
            }
            logedInUser
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun signUp(): FirebaseUser? {
        val googleCredential = GoogleAuthProvider.getCredential(authCredential.googleIdToken, null)
        return try {
            val signInResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = signInResult.additionalUserInfo?.isNewUser ?: false
            val logedInUser = signInResult.user
            if (logedInUser != null && isNewUser) {
                logedInUser.uid.let { uid ->
                    storageService.addUser(
                        User(
                            uid = uid,
                            email = logedInUser.email ?: "",
                            username = uid.dropLast(8),
                            firstName = authCredential.givenName ?: "",
                            lastName = authCredential.familyName ?: "",
                            profilePictureUri = authCredential.profilePictureUri
                        )
                    )
                }
            }
            logedInUser
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}