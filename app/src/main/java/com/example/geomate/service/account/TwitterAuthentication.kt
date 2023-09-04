package com.example.geomate.service.account

import android.app.Activity
import com.example.geomate.ext.toUser
import com.example.geomate.service.storage.StorageService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.tasks.await

class TwitterAuthentication(
    private val activity: Activity,
    private val storageService: StorageService,
) : Authentication {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override suspend fun signIn(): FirebaseUser? {
        val provider = OAuthProvider.newBuilder("twitter.com").build()
        return try {
            val result = auth.startActivityForSignInWithProvider(activity, provider).await()
            val isNewUser = result.additionalUserInfo?.isNewUser ?: false
            result.additionalUserInfo?.profile
            val user = result.user
            if (user != null && isNewUser) {
                storageService.addUser(user.toUser())
            }
            user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun signUp(): FirebaseUser? {
        TODO("Not yet implemented")
    }

}