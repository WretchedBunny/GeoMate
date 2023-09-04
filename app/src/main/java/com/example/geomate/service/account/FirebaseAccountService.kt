package com.example.geomate.service.account

import com.example.geomate.service.storage.StorageService
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAccountService(
    private val firebaseAuth: FirebaseAuth,
    override val storageService: StorageService,
    private val oneTapClient: SignInClient,
) : AccountService {
    override suspend fun sendRecoveryEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun signOut(): Boolean {
        return try {
            when (firebaseAuth.currentUser?.getIdToken(false)?.result?.signInProvider) {
                "password" -> {
                    firebaseAuth.signOut()
                    true
                }

                "google" -> {
                    oneTapClient.signOut()
                    firebaseAuth.signOut()
                    true
                }

                "twitter" -> {
                    false
                }

                "facebook" -> {
                    false
                }

                else -> false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}