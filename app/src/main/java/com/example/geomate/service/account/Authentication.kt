package com.example.geomate.service.account

import com.example.geomate.model.User
import com.google.firebase.auth.FirebaseUser

interface Authentication {
    suspend fun signIn(): FirebaseUser?
    suspend fun signUp(): FirebaseUser?
    suspend fun signOut(): FirebaseUser?
    fun FirebaseUser.createUser(
        username: String? = "",
        firstName: String? = "",
        lastName: String? = "",
        bio: String? = "",
    ): User
}
