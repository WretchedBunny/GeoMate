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
        auth.signInWithCredential(authCredential).await()
        return user
    }

    override suspend fun signUp() {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

}