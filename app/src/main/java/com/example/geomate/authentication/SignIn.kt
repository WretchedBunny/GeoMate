package com.example.geomate.authentication

import com.google.firebase.auth.FirebaseUser

fun interface SignIn {
    suspend fun  signIn(): FirebaseUser?
}