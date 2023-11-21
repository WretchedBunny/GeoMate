package com.example.geomate.localsearch

object Contains : Rule {
    override fun match(value: String, searchQuery: String): Boolean {
        return value.contains(searchQuery, true)
    }
}