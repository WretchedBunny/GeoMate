package com.example.geomate.localsearch

object Abbreviation : Rule {
    override fun match(value: String, searchQuery: String): Boolean = value
        .lowercase()
        .split(" ")
        .map { it[0] }
        .joinToString("")
        .contains(searchQuery.lowercase())
}