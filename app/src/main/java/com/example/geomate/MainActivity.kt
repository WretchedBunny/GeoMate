package com.example.geomate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.geomate.ui.screens.map.MapScreen
import com.example.geomate.ui.theme.GeoMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeoMateTheme {
                MapScreen(
                    navigateToGroups = { /* TODO: Navigate to groups */ },
                    navigateToSocial = { /* TODO: Navigate to social */ }
                )
            }
        }
    }
}