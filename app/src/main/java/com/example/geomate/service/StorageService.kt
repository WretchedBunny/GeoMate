package com.example.geomate.service

import android.util.Log
import com.example.geomate.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val TAG = "StorageService"

class StorageService(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
) {
    suspend fun addUser(user: User) {
        Log.d(TAG, "Trying to add new user.")
        fireStore.collection("user").add(user).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }.await()
    }

}