package com.example.geomate.model

data class Group(
    val uid: String = "",
    val name: String = "",
    val owner: String = "",
    val users: List<User> = listOf()
)