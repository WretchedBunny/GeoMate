package com.example.geomate.service.account

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface Authentication {
    val auth: FirebaseAuth
    val user: FirebaseUser?
    suspend fun signIn(authCredential: AuthCredential): FirebaseUser?
    suspend fun signUp()
}