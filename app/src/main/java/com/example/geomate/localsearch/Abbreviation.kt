package com.example.geomate.localsearch

object Abbreviation : Rule {
    override fun match(value: String, searchQuery: String): Boolean {
        return searchQuery.isNotBlank() && value.split(" ")
            .map { it[0] }
            .joinToString("")
            .contains(searchQuery, true)
    }
}