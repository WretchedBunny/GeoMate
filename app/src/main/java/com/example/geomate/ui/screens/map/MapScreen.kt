package com.example.geomate.ui.screens.map

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.geomate.R
import com.example.geomate.ui.components.BottomNavigationBar
import com.example.geomate.ui.components.GeoMateFAB
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.IconWithNotification
import com.example.geomate.ui.components.LeadingIcon
import com.example.geomate.ui.components.TrailingIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navigateToGroups: () -> Unit,
    navigateToSocial: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val mapStyleId = if (isSystemInDarkTheme()) R.raw.map_style_dark else R.raw.map_style_light

    Scaffold(
        floatingActionButton = {
            GeoMateFAB(icon = Icons.Outlined.GpsFixed) {
                // TODO: Place the camera on user's current location
            }
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = Destinations.MAP_ROUTE,
                navigateToMap = { },
                navigateToGroups = navigateToGroups,
                navigateToSocial = navigateToSocial,
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            GoogleMap(
                uiSettings = MapUiSettings(
                    compassEnabled = false,
                    zoomControlsEnabled = false
                ),
                properties = MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, mapStyleId)
                )
            ) { }

            var searchQuery by remember { mutableStateOf("") }
            GeoMateTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                leadingIcons = listOf(
                    LeadingIcon(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null,
                                modifier = it,
                            )
                        }
                    )
                ),
                trailingIcons = listOf(
                    TrailingIcon(
                        icon = {
                            IconWithNotification(
                                icon = Icons.Outlined.Notifications,
                                notificationsCount = 4,
                                notificationsForegroundColor = MaterialTheme.colorScheme.onPrimary,
                                notificationsBackgroundColor = MaterialTheme.colorScheme.primary,
                                modifier = it,
                            )
                        },
                        onClick = { /* TODO: Navigate to the notification screen */ }
                    )
                ),
                placeholder = stringResource(id = R.string.email_placeholder),
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MapScreenPreview() {
    GeoMateTheme {
        MapScreen(
            navigateToGroups = { },
            navigateToSocial = { }
        )
    }
}