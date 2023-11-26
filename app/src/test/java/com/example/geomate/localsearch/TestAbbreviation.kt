package com.example.geomate.localsearch

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TestAbbreviation {
    @Test
    fun `match should return false if search query is empty`() {
        val actual = Abbreviation.match("Hello, Testing World!", "")
        assertFalse(actual)
    }

    @Test
    fun `match should return false if search query is blank`() {
        val actual = Abbreviation.match("Hello, Testing World!", "   ")
        assertFalse(actual)
    }

    @Test
    fun `match should return true if first letters of value contain search query (case sensitive, full abbreviation)`() {
        val actual = Abbreviation.match("Hello, Testing World!", "HTW")
        assertTrue(actual)
    }

    @Test
    fun `match should return true if first letters of value contain search query (case sensitive, part of abbreviation)`() {
        val actual = Abbreviation.match("Hello, Testing World!", "TW")
        assertTrue(actual)
    }

    @Test
    fun `match should return true if first letters of value contain search query (case insensitive, full abbreviation)`() {
        val actual = Abbreviation.match("Hello, Testing World!", "htw")
        assertTrue(actual)
    }

    @Test
    fun `match should return true if first letters of value contain search query (case insensitive, part of abbreviation)`() {
        val actual = Abbreviation.match("Hello, Testing World!", "tw")
        assertTrue(actual)
    }

    @Test
    fun `match should return false if first letters of value not contain search query (no intersection)`() {
        val actual = Abbreviation.match("Hello, Testing World!", "foo")
        assertFalse(actual)
    }

    @Test
    fun `match should return false if first letters of value not contain search query (skip one letter)`() {
        val actual = Abbreviation.match("Hello, Testing World!", "HW")
        assertFalse(actual)
    }
}