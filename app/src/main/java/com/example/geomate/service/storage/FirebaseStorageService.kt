package com.example.geomate.service.storage

import com.example.geomate.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseStorageService(private val fireStore: FirebaseFirestore) : StorageService {
    override suspend fun addUser(user: User) {
        fireStore.collection("user").add(user).await()
    }

    override suspend fun getUser(uid: String): User? {
        return fireStore.collection("user")
            .whereEqualTo("uid", uid)
            .get().await()
            .toObjects(User::class.java).firstOrNull()
    }
}