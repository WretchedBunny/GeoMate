package com.example.geomate.authentication

import com.example.geomate.data.models.User
import com.google.firebase.auth.FirebaseUser

interface Authentication {
    suspend fun signIn(): FirebaseUser?
    suspend fun signUp(): FirebaseUser?
    fun createUser(user: FirebaseUser): User = User(
        uid = user.uid,
        email = user.email ?: "",
        username = user.email?.substringBefore('@') ?: user.uid.take(20),
        firstName = user.displayName?.substringBeforeLast(' ') ?: "",
        lastName = user.displayName?.substringAfterLast(' ') ?: "",
        bio = ""
    )
}
