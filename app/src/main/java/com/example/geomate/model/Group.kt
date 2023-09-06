package com.example.geomate.model

data class Group(
    val name: String = "",
    val users: List<User> = listOf()
)