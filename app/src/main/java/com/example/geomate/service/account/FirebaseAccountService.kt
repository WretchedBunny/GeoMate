package com.example.geomate.service.account

import com.example.geomate.service.storage.StorageService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAccountService(
    private val firebaseAuth: FirebaseAuth,
    override val storageService: StorageService,
) : AccountService {
    override suspend fun sendRecoveryEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }
}