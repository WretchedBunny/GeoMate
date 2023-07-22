package com.example.geomate.ext

import android.util.Patterns

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.trim().length >= 8
}