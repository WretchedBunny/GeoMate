package com.example.geomate.service.storage

import android.util.Log
import com.example.geomate.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val TAG = "StorageService"

class FirebaseStorageService(private val fireStore: FirebaseFirestore) : StorageService {
    override suspend fun addUser(user: User) {
        fireStore.collection("user").add(user).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }.await()
    }

    override suspend fun loggedForFirstTime(uid: String) {
        TODO("Not yet implemented")
    }
}