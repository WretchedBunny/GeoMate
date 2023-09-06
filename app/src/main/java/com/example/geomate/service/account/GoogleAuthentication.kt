package com.example.geomate.service.account

import android.content.IntentSender
import com.example.geomate.model.User
import com.example.geomate.service.bucket.BucketService
import com.example.geomate.service.storage.StorageService
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthentication(
    private val storageService: StorageService,
    private val bucketService: BucketService,
    private val authCredential: SignInCredential,
) : Authentication {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
                oneTapClient.beginSignIn(requestBuilder.setAutoSelectEnabled(true).build()).await()
            } catch (e: Exception) {
                null
            }
            return result?.pendingIntent?.intentSender
        }

        suspend fun getSignUpIntentSender(oneTapClient: SignInClient): IntentSender? {
            val result = try {
                oneTapClient.beginSignIn(requestBuilder.setAutoSelectEnabled(false).build()).await()
            } catch (e: Exception) {
                null
            }
            return result?.pendingIntent?.intentSender
        }
    }

    override suspend fun signIn(): FirebaseUser? {
        val googleCredential = GoogleAuthProvider.getCredential(authCredential.googleIdToken, null)
        return try {
            val result = auth.signInWithCredential(googleCredential).await()
            val isNewUser = result.additionalUserInfo?.isNewUser ?: false
            val user = result.user
            if (user != null && isNewUser) {
                storageService.addUser(
                    user.createUser(
                        firstName = authCredential.givenName,
                        lastName = authCredential.familyName
                    )
                )
                authCredential.profilePictureUri?.let { profilePictureUri ->
                    bucketService.store(user.uid, profilePictureUri)
                }
            }
            user
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signUp(): FirebaseUser? {
        val googleCredential = GoogleAuthProvider.getCredential(authCredential.googleIdToken, null)
        return try {
            val result = auth.signInWithCredential(googleCredential).await()
            val isNewUser = result.additionalUserInfo?.isNewUser ?: false
            val user = result.user
            if (user != null && isNewUser) {
                storageService.addUser(
                    user.createUser(
                        firstName = authCredential.givenName,
                        lastName = authCredential.familyName
                    )
                )
                /*
                authCredential.profilePictureUri?.let { profilePictureUri ->
                    bucketService.store(user.uid, profilePictureUri)
                }
                 */
            }
            user
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signOut(): FirebaseUser? {
        auth.signOut()
        return auth.currentUser
    }

    override fun FirebaseUser.createUser(
        username: String?,
        firstName: String?,
        lastName: String?,
        bio: String?,
    ): User = User(
        uid = this.uid,
        email = this.email ?: "",
        username = this.email?.substringBefore('@') ?: this.uid.take(20),
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        bio = ""
    )
}