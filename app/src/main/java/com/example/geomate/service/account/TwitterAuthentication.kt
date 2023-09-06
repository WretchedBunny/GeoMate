package com.example.geomate.service.account

import android.app.Activity
import com.example.geomate.model.User
import com.example.geomate.service.bucket.BucketService
import com.example.geomate.service.storage.StorageService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.tasks.await

class TwitterAuthentication(
    private val activity: Activity,
    private val storageService: StorageService,
    private val bucketService: BucketService,
) : Authentication {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override suspend fun signIn(): FirebaseUser? = auth()
    override suspend fun signUp(): FirebaseUser? = auth()
    override suspend fun signOut(): FirebaseUser? {
        auth.signOut()
        return auth.currentUser
    }

    private suspend fun auth(): FirebaseUser? {
        val provider = OAuthProvider.newBuilder("twitter.com").build()
        return try {
            val result = auth.startActivityForSignInWithProvider(activity, provider).await()
            val isNewUser = result.additionalUserInfo?.isNewUser ?: false
            val profile = result.additionalUserInfo?.profile
            val user = result.user
            if (user != null && isNewUser) {
                storageService.addUser(
                    user.createUser(
                        username = profile?.get("screen_name") as String?,
                        bio = profile?.get("description") as String?
                    )
                )
                /*
                profile?.let {
                    val uri = Uri.parse(it["profile_image_url"].toString())
                    bucketService.store(user.uid, uri)
                }
                 */
            }
            user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun FirebaseUser.createUser(
        username: String?,
        firstName: String?,
        lastName: String?,
        bio: String?,
    ): User = User(
        uid = this.uid,
        email = this.email ?: "",
        username = username ?: this.uid.take(20),
        firstName = this.displayName?.substringBeforeLast(' ') ?: "",
        lastName = this.displayName?.substringAfterLast(' ') ?: "",
        bio = bio ?: "",
    )
}
