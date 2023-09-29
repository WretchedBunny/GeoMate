package com.example.geomate.authentication

import android.app.Activity
import android.net.Uri
import com.example.geomate.data.repositories.UsersRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.tasks.await

class TwitterAuthentication(
    private val usersRepository: UsersRepository,
    private val activity: Activity,
) : Authentication {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private suspend fun auth(): FirebaseUser? = try {
        val provider = OAuthProvider.newBuilder("twitter.com").build()
        val result = auth.startActivityForSignInWithProvider(activity, provider).await()
        val isNewUser = result.additionalUserInfo?.isNewUser ?: false
        val profile = result.additionalUserInfo?.profile
        val user = result.user
        if (user != null && isNewUser) {
            usersRepository.add(createUser(user))
            profile?.let {
                val uri = Uri.parse(it["profile_image_url"].toString())
                usersRepository.addProfilePicture(user.uid, uri)
            }
        }
        user
    } catch (e: Exception) {
        null
    }

    override suspend fun signIn(): FirebaseUser? = auth()
    override suspend fun signUp(): FirebaseUser? = auth()
}
