package com.example.geomate.ui.screens.map

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.geomate.R
import com.example.geomate.ui.components.BottomNavigationBar
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.theme.GeoMateTheme
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapScreen(
    navigateToGroups: () -> Unit,
    navigateToSocial: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val mapStyleId = if (isSystemInDarkTheme()) R.raw.map_style_dark else R.raw.map_style_light

    Box(modifier = modifier) {
        GoogleMap(
            uiSettings = MapUiSettings(
                compassEnabled = false,
                zoomControlsEnabled = false
            ),
            properties = MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, mapStyleId)
            )
        ) { }
        BottomNavigationBar(
            currentRoute = Destinations.MAP_ROUTE,
            navigateToMap = { },
            navigateToGroups = navigateToGroups,
            navigateToSocial = navigateToSocial,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview
@Composable
private fun MapScreenPreview() {
    GeoMateTheme {
        MapScreen(
            navigateToGroups = { },
            navigateToSocial = { }
        )
    }
}