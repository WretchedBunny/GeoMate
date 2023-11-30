package com.example.geomate.localsearch

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TestContains {
    @Test
    fun `match should return false if search query is empty`() {
        val actual = Contains.match("Hello, World!", "")
        assertFalse(actual)
    }

    @Test
    fun `match should return false if search query is blank`() {
        val actual = Contains.match("Hello, World!", "   ")
        assertFalse(actual)
    }

    @Test
    fun `match should return true if value contains search query (case insensitive)`() {
        val actual = Contains.match("Hello, World!", "world")
        assertTrue(actual)
    }

    @Test
    fun `match should return true if value contains search query (case sensitive)`() {
        val actual = Contains.match("Hello, World!", "World")
        assertTrue(actual)
    }

    @Test
    fun `match should return false if value does not contain search query`() {
        val actual = Contains.match("Hello, World!", "foo")
        assertFalse(actual)
    }
}