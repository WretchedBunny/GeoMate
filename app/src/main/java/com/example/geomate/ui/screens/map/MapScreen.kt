package com.example.geomate.ui.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.geomate.ui.theme.GeoMateTheme
import com.google.maps.android.compose.GoogleMap

@Composable
fun MapScreen() {
    GoogleMap {

    }
}

@Preview
@Composable
private fun MapScreenPreview() {
    GeoMateTheme {
        MapScreen()
    }
}