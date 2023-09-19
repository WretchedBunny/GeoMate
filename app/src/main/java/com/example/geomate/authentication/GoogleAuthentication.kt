package com.example.geomate.service.authentication

import android.content.IntentSender
import com.example.geomate.data.models.User
import com.example.geomate.data.repositories.UsersRepository
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthentication(
    private val usersRepository: UsersRepository,
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

        suspend fun getSignInIntentSender(oneTapClient: SignInClient): IntentSender? = try {
            oneTapClient.beginSignIn(requestBuilder.setAutoSelectEnabled(true).build()).await()
        } catch (e: Exception) {
            null
        }?.pendingIntent?.intentSender

        suspend fun getSignUpIntentSender(oneTapClient: SignInClient): IntentSender? = try {
            oneTapClient.beginSignIn(requestBuilder.setAutoSelectEnabled(false).build()).await()
        } catch (e: Exception) {
            null
        }?.pendingIntent?.intentSender
    }
    override suspend fun signIn(): FirebaseUser? = try {
        val googleCredential = GoogleAuthProvider.getCredential(authCredential.googleIdToken, null)
        val result = auth.signInWithCredential(googleCredential).await()
        val isNewUser = result.additionalUserInfo?.isNewUser ?: false
        val user = result.user
        if (user != null && isNewUser) {
            usersRepository.add(createUser(user))
            authCredential.profilePictureUri?.let { profilePictureUri ->
                usersRepository.addProfilePicture(user.uid, profilePictureUri)
            }
        }
        user
    } catch (e: Exception) {
        null
    }

    override suspend fun signUp(): FirebaseUser? = try {
        val googleCredential = GoogleAuthProvider.getCredential(authCredential.googleIdToken, null)
        val result = auth.signInWithCredential(googleCredential).await()
        val isNewUser = result.additionalUserInfo?.isNewUser ?: false
        val user = result.user
        if (user != null && isNewUser) {
            usersRepository.add(createUser(user))
            authCredential.profilePictureUri?.let { profilePictureUri ->
                usersRepository.addProfilePicture(user.uid, profilePictureUri)
            }
        }
        user
    } catch (e: Exception) {
        null
    }

    override suspend fun signOut() = auth.signOut()

    override fun createUser(user: FirebaseUser): User = User(
        uid = user.uid,
        email = user.email ?: "",
        username = user.email?.substringBefore('@') ?: user.uid.take(20),
        firstName = user.displayName?.substringBeforeLast(' ') ?: "",
        lastName = user.displayName?.substringAfterLast(' ') ?: "",
        bio = ""
    )
}