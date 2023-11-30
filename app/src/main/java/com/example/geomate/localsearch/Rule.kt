package com.example.geomate.localsearch

fun interface Rule {
    fun match(value: String, searchQuery: String): Boolean
}