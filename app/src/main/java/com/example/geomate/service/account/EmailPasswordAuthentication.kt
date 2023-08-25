package com.example.geomate.service.account

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class EmailPasswordAuthentication(
    override val auth: FirebaseAuth,
    val email: String,
    val password: String,
) : Authentication {
    override val user: FirebaseUser? = auth.currentUser
    override suspend fun signIn(authCredential: AuthCredential): FirebaseUser? {
        return try {
            auth.signInWithCredential(authCredential).await()
            user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun signUp(): FirebaseUser? {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}