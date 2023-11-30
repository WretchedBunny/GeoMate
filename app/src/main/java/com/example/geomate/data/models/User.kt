package com.example.geomate.data.models

import java.util.Date

data class User(
    val uid: String = "",
    val email: String = "",
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
    val joined: Date = Date(),
)
