package com.example.geomate.service.account

import com.google.firebase.auth.FirebaseUser

interface Authentication {
    suspend fun signIn(): FirebaseUser?
    suspend fun signUp(): FirebaseUser?
}
