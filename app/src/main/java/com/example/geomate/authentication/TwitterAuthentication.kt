package com.example.geomate.service.authentication

import android.app.Activity
import android.net.Uri
import com.example.geomate.data.models.User
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
    override suspend fun signIn(): FirebaseUser? = auth()
    override suspend fun signUp(): FirebaseUser? = auth()
    override suspend fun signOut() = auth.signOut()

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

    override fun createUser(user: FirebaseUser): User = User(
        uid = user.uid,
        email = user.email ?: "",
        username = user.uid.take(20),
        firstName = user.displayName?.substringBeforeLast(' ') ?: "",
        lastName = user.displayName?.substringAfterLast(' ') ?: "",
        bio = "",
    )
}
