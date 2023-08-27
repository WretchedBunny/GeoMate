package com.example.geomate.ext

import com.example.geomate.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUser(): User = User(
    uid = this.uid,
    email = this.email ?: "",
    username = this.email?.substringBefore('@') ?: this.uid.take(20),
    firstName = this.displayName?.substringBeforeLast(' ') ?: "",
    lastName = this.displayName?.substringAfterLast(' ') ?: "",
)