package com.example.geomate.service.account

import android.net.Uri
import com.example.geomate.model.User
import com.example.geomate.service.bucket.BucketService
import com.example.geomate.service.storage.StorageService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class EmailPasswordAuthentication(
    private val email: String,
    private val password: String,
    private val username: String = "",
    private val firstName: String = "",
    private val lastName: String = "",
    private val bio: String = "",
    private val uri: Uri? = null,
    private val storageService: StorageService,
    private val bucketService: BucketService,

    ) : Authentication {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signIn(): FirebaseUser? {
        return try {
            auth.signInWithEmailAndPassword(email, password).await().user
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signUp(): FirebaseUser? {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user
            if (user != null) {
                storageService.addUser(
                    user.createUser(
                        username = username,
                        firstName = firstName,
                        lastName = lastName,
                        bio = bio
                    )
                )
                /*
                uri?.let {
                    bucketService.store(user.uid, it)
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
        username = username ?: this.uid.take(20),
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        bio = bio ?: ""
    )
}