package com.example.geomate.authentication

import com.example.geomate.data.models.User
import com.google.firebase.auth.FirebaseUser

interface Authentication {
    suspend fun signIn(): FirebaseUser?
    suspend fun signUp(): FirebaseUser?
    suspend fun signOut()
    fun createUser(user: FirebaseUser): User
}
