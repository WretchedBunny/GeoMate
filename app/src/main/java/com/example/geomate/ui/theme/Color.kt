package com.example.geomate.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object GeoMateColors {
    // Specific colors for light theme
    val Brown50 = Color(0xFF7B5401)
    val Beige30 = Color(0xFFF4E8DA)
    val Beige10 = Color(0xFFFFF7F0)
    val Gray50 = Color(0xFF464845)

    // Specific colors for dark theme
    val Orange40 = Color(0xFFFFAC62)
    val Gray60 = Color(0xFF444444)
    val Gray80 = Color(0xFF363636)
    val Gray90 = Color(0xFF2A2A2A)

    // Common colors
    val White = Color(0xFFFFFFFF)
    val Red40 = Color(0xFFFF4040)
}

interface GoeMateColorScheme{
    val primary: Color
    val onPrimary: Color
    val secondary: Color
    val onSecondary: Color
    val surface: Color
    val surfaceVariant: Color
    val onSurface: Color
    val onSurfaceVariant: Color
    val backgroundLight: Color
    val backgroundDark: Color
    val onBackground: Color
    val error: Color
}

object GeoMateLightColorScheme : GoeMateColorScheme {
    override val primary: Color = GeoMateColors.Brown50
    override val onPrimary: Color = GeoMateColors.White
    override val secondary: Color = GeoMateColors.Beige30
    override val onSecondary: Color = GeoMateColors.Gray50
    override val surface: Color = GeoMateColors.Beige10
    override val surfaceVariant: Color = GeoMateColors.White
    override val onSurface: Color = GeoMateColors.Gray50
    override val onSurfaceVariant: Color = GeoMateColors.Gray50
    override val backgroundLight: Color = GeoMateColors.Beige10
    override val backgroundDark: Color = GeoMateColors.Beige30
    override val onBackground: Color = GeoMateColors.Gray50
    override val error: Color = GeoMateColors.Red40
}

object GeoMateDarkColorScheme : GoeMateColorScheme {
    override val primary: Color = GeoMateColors.Orange40
    override val onPrimary: Color = GeoMateColors.Gray90
    override val secondary: Color = GeoMateColors.Gray80
    override val onSecondary: Color = GeoMateColors.White
    override val surface: Color = GeoMateColors.Gray80
    override val surfaceVariant: Color = GeoMateColors.Gray60
    override val onSurface: Color = GeoMateColors.White
    override val onSurfaceVariant: Color = GeoMateColors.White
    override val backgroundLight: Color = GeoMateColors.Gray90
    override val backgroundDark: Color = GeoMateColors.Gray90
    override val onBackground: Color = GeoMateColors.White
    override val error: Color = GeoMateColors.Red40
}

fun mapColors(
    darkTheme: Boolean,
    colorScheme: GoeMateColorScheme
) = if (darkTheme) {
    darkColorScheme(
        primary = colorScheme.primary,
        onPrimary = colorScheme.onPrimary,
        secondary = colorScheme.secondary,
        onSecondary = colorScheme.onSecondary,
        surface = colorScheme.surface,
        onSurface = colorScheme.onSurface,
        surfaceVariant = colorScheme.surfaceVariant,
        onSurfaceVariant = colorScheme.onSurfaceVariant,
        background = colorScheme.backgroundLight,
        onBackground = colorScheme.onBackground,
        error = colorScheme.error
    )
} else {
    lightColorScheme(
        primary = colorScheme.primary,
        onPrimary = colorScheme.onPrimary,
        secondary = colorScheme.secondary,
        onSecondary = colorScheme.onSecondary,
        surface = colorScheme.surface,
        onSurface = colorScheme.onSurface,
        surfaceVariant = colorScheme.surfaceVariant,
        onSurfaceVariant = colorScheme.onSurfaceVariant,
        background = colorScheme.backgroundLight,
        onBackground = colorScheme.onBackground,
        error = colorScheme.error
    )
}