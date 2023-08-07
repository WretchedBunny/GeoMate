package com.example.geomate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.geomate.ui.screens.map.MapScreen
import com.example.geomate.ui.screens.map.MapViewModel
import com.example.geomate.ui.theme.GeoMateTheme
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = MapViewModel(
                application = application,
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            )
            val uiState by viewModel.uiState.collectAsState()

            GeoMateTheme {
                MapScreen(
                    uiState = uiState,
                    toggleAll = viewModel::toggleAll,
                    toggleGroup = viewModel::toggleGroup,
                    updateSearchQuery = viewModel::updateSearchQuery,
                    pointCameraOnUser = { /* TODO: Point camera on user */ },
                    navigateToGroups = { /* TODO: Navigate to groups */ },
                    navigateToSocial = { /* TODO: Navigate to social */ }
                )
            }
        }
    }
}