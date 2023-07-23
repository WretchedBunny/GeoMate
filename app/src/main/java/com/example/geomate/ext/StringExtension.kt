package com.example.geomate.ext

import android.util.Patterns

private const val REGEX_STRONG_PASSWORD =
    "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])(?=.{8,255})"
private const val REGEX_USERNAME = "^[a-zA-Z0-9_]{5,20}$"

fun String.isEmailValid(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPasswordValid(): Boolean {
    return REGEX_STRONG_PASSWORD.toRegex().containsMatchIn(this)
}

fun String.isUsernameValid(): Boolean {
    return REGEX_USERNAME.toRegex().containsMatchIn(this)
}
