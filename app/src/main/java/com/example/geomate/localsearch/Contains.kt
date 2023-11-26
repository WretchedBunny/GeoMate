package com.example.geomate.localsearch

object Contains : Rule {
    override fun match(value: String, searchQuery: String): Boolean {
        return searchQuery.isNotBlank() && value.contains(searchQuery, true)
    }
}