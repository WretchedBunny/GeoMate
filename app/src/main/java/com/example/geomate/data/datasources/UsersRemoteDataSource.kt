package com.example.geomate.data.datasources

import android.net.Uri
import com.example.geomate.data.models.User
import com.example.geomate.ext.snapshotFlow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class UsersRemoteDataSource(
    private val fireAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val fireBucket: FirebaseStorage,
) : UsersDataSource {
    override suspend fun get(userId: String): Flow<User?> = fireStore
        .collection("users")
        .whereEqualTo("uid", userId)
        .snapshotFlow()
        .map { it.toObjects(User::class.java).firstOrNull() }

    override suspend fun getAll(userIds: List<String>): Flow<List<User>> = fireStore
        .collection("users")
        .whereIn("uid", userIds)
        .snapshotFlow()
        .map { it.toObjects(User::class.java) }

    override suspend fun add(user: User) {
        fireStore.collection("users").add(user).await()
    }

    override suspend fun remove(user: User) {
        fireStore.collection("users")
            .whereEqualTo("uid", user.uid)
            .get().await()
            .documents.first().reference.delete()
    }

    override suspend fun getProfilePicture(userId: String): Flow<Uri> = flow {
        val reference = fireBucket.reference.child("profile-pictures/$userId")
        val uri = try {
            reference.downloadUrl.await()
        } catch (e: Exception) { Uri.EMPTY }
        emit(uri)
    }

    override suspend fun addProfilePicture(userId: String, uri: Uri) {
        val reference = fireBucket.reference.child("profile-pictures/$userId")
        reference.putFile(uri)
    }

    override suspend fun sendRecoveryEmail(email: String) {
        fireAuth.sendPasswordResetEmail(email).await()
    }
}