package com.example.geomate.ext

import android.util.Patterns

private const val REGEX_STRONG_PASSWORD =
    "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
private const val REGEX_FIRST_NAME = "^[A-Z][a-zA-Z]+(?:-[A-Z][A-Za-z]+)?\$"
private const val REGEX_LAST_NAME = "^[A-Z][a-zA-Z]+(?: [A-Z][A-Za-z]+)?\$"
private const val REGEX_USERNAME = "^[a-zA-Z0-9_]{5,20}$"

fun String.isEmailValid(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPasswordValid(): Boolean {
    return REGEX_STRONG_PASSWORD.toRegex().matches(this)
}

fun String.isFirstNameValid(): Boolean {
    return REGEX_FIRST_NAME.toRegex().matches(this)
}

fun String.isLastNameValid(): Boolean {
    return REGEX_LAST_NAME.toRegex().matches(this)
}

fun String.isUsernameValid(): Boolean {
    return REGEX_USERNAME.toRegex().matches(this)
}
