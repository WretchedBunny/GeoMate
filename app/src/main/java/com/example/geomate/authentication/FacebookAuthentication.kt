package com.example.geomate.authentication

import android.content.Intent
import androidx.core.content.IntentCompat
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
) : SignIn, SignUp {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object {
        private const val resultKey = "com.facebook.LoginFragment:Result"

        fun getTokenFromIntent(intent: Intent?): AccessToken? = intent?.let {
            IntentCompat.getParcelableExtra(it, resultKey, LoginClient.Result::class.java)?.token
        }
    }

    private suspend fun auth(): FirebaseUser? = accessToken?.let {
        return try {
            val facebookAuthCredential = FacebookAuthProvider.getCredential(it.token)
            val result = auth.signInWithCredential(facebookAuthCredential).await()
            val isNewUser = result.additionalUserInfo?.isNewUser ?: false
            val user = result.user
            if (user != null && isNewUser) {
                usersRepository.add(createUser(user))
            }
            user
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signIn(): FirebaseUser? = auth()

    override suspend fun signUp(): FirebaseUser? = auth()
}