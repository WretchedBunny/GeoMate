package com.example.geomate.service.account

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class GoogleSignInAuthentication(override val auth: FirebaseAuth) : Authentication {
    override val user: FirebaseUser? = auth.currentUser

    override suspend fun signIn(authCredential: AuthCredential): FirebaseAuth?{
        TODO("Not yet implemented")
    }

    override suspend fun signUp() {
        TODO("Not yet implemented")
    }

}