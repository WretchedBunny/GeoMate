package com.example.geomate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val darkColorScheme = GeoMateDarkColorScheme
private val lightColorScheme = GeoMateLightColorScheme

@Composable
fun GeoMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color = colorScheme.backgroundLight)
        systemUiController.setStatusBarColor(color = colorScheme.backgroundLight)
    }

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = mapColors(darkTheme, colorScheme),
            typography = Typography,
            content = content
        )
    }
}