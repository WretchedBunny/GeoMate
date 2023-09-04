package com.example.geomate.service.account

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class EmailPasswordAuthentication(
    private val email: String,
    private val password: String,
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
            auth.createUserWithEmailAndPassword(email, password).await().user
        } catch (e: Exception) {
            null
        }
    }
}