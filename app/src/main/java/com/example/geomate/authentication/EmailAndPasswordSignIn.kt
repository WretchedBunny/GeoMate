package com.example.geomate.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class EmailAndPasswordSignIn(
    private val email: String,
    private val password: String,
) : SignIn {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signIn(): FirebaseUser? = try {
        auth.signInWithEmailAndPassword(email, password).await().user
    } catch (e: Exception) {
        null
    }
}