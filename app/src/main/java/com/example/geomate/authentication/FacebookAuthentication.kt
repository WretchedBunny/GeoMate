package com.example.geomate.authentication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.content.IntentCompat
import com.example.geomate.data.models.User
import com.example.geomate.data.repositories.UsersRepository
import com.facebook.AccessToken
import com.facebook.login.LoginClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FacebookAuthentication(
    private val usersRepository: UsersRepository,
    private val accessToken: AccessToken?,
) : Authentication {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object {
        @SuppressLint("RestrictedApi")
        fun getTokenFromIntent(intent: Intent?): AccessToken? {
            val resultKey = "com.facebook.LoginFragment:Result"
            if (intent == null) {
                return null
            }
            val loginClientResult = IntentCompat.getParcelableExtra(
                intent,
                resultKey,
                LoginClient.Result::class.java
            )
            return loginClientResult?.token
        }
    }

    private suspend fun auth(): FirebaseUser? {
        try {
            if (accessToken == null) {
                return null
            }
            val facebookAuthCredential = FacebookAuthProvider.getCredential(accessToken.token)
            val result = auth.signInWithCredential(facebookAuthCredential).await()
            val isNewUser = result.additionalUserInfo?.isNewUser ?: false
            val user = result.user
            if (user != null && isNewUser) {
                usersRepository.add(createUser(user))
            }
            return user
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun signIn(): FirebaseUser? = auth()

    override suspend fun signUp(): FirebaseUser? = auth()

    override suspend fun signOut() = auth.signOut()

    override fun createUser(user: FirebaseUser): User = User(
        uid = user.uid,
        email = user.email ?: "",
        username = user.uid.take(20),
        firstName = user.displayName?.substringBeforeLast(' ') ?: "",
        lastName = user.displayName?.substringAfterLast(' ') ?: "",
        bio = "",
    )
}