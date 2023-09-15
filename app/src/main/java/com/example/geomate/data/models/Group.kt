package com.example.geomate.data.models

data class Group(
    val uid: String = "",
    val name: String = "",
    val owner: String = "",
    val users: List<String> = listOf()
)