package com.example.geomate.ui.screens.map

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.geomate.R
import com.example.geomate.model.Group
import com.example.geomate.model.User
import com.example.geomate.ui.components.BottomNavigationBar
import com.example.geomate.ui.components.ChipsRow
import com.example.geomate.ui.components.GeoMateFAB
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.IconWithNotification
import com.example.geomate.ui.components.TextFieldIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import kotlinx.coroutines.launch

fun NavGraphBuilder.map(
    viewModel: MapViewModel,
    navController: NavController,
) {
    composable(Destinations.MAP_ROUTE) {
        val uiState by viewModel.uiState.collectAsState()
        MapScreen(
            uiState = uiState,
            viewModel = viewModel,
            navController = navController
        )
    }
}

fun NavController.navigateToMap() {
    popBackStack()
    navigate(Destinations.MAP_ROUTE) {
        launchSingleTop = false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    uiState: MapUiState,
    viewModel: MapViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var user: User? by rememberSaveable { mutableStateOf(null) }
    val mapStyleId = if (isSystemInDarkTheme()) R.raw.map_style_dark else R.raw.map_style_light

    LaunchedEffect(Firebase.auth.currentUser) {
        coroutineScope.launch {
            Firebase.auth.currentUser?.uid?.let {
                user = viewModel.getUser(it)
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            GeoMateFAB(
                icon = Icons.Outlined.GpsFixed,
                onClick = viewModel::pointCameraOnUser
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = Destinations.MAP_ROUTE,
                navigateToMap = { },
                navigateToGroups = { /* navController::navigateToGroups */ },
                navigateToSocial = { /* navController::navigateToSocial */ },
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            GoogleMap(
                cameraPositionState = uiState.cameraPosition,
                uiSettings = MapUiSettings(
                    compassEnabled = false,
                    zoomControlsEnabled = false
                ),
                properties = MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, mapStyleId)
                ),
            ) { }

            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(vertical = MaterialTheme.spacing.medium)
            ) {
                GeoMateTextField(
                    value = uiState.searchQuery,
                    onValueChange = viewModel::updateSearchQuery,
                    leadingIcons = listOf(
                        TextFieldIcon {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null,
                                modifier = it,
                            )
                        }
                    ),
                    trailingIcons = listOf(
                        TextFieldIcon(
                            onClick = { /* TODO: Navigate to the notification screen */ }
                        ) {
                            IconWithNotification(
                                icon = Icons.Outlined.Notifications,
                                notificationsCount = 4,
                                notificationsForegroundColor = MaterialTheme.colorScheme.onPrimary,
                                notificationsBackgroundColor = MaterialTheme.colorScheme.primary,
                                modifier = it,
                            )
                        },
                        TextFieldIcon(
                            onClick = { /* TODO: Navigate to the profile screen */ }
                        ) { modifier ->
                            // TODO: Get download uri from BucketService if exists
                            //  download user's profile picture and cache it
                            val drawableId =
                                if (isSystemInDarkTheme()) R.drawable.profile_picture_placeholder_dark
                                else R.drawable.profile_picture_placeholder_light
                            Image(
                                painter = painterResource(id = drawableId),
                                contentDescription = null,
                                modifier = modifier
                                    .size(25.dp)
                                    .clip(CircleShape)
                            )
                        }
                    ),
                    placeholder = stringResource(id = R.string.users_search_placeholder),
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
                )
                ChipsRow(
                    groups = uiState.groups,
                    isAllSelected = uiState.isAllSelected,
                    toggleGroup = viewModel::toggleGroup,
                    toggleAllGroups = viewModel::toggleAllGroups,
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MapScreenPreview() {
    GeoMateTheme {
        MapScreen(
            uiState = MapUiState(
                groups = listOf(
                    Group(name = "University", isSelected = true),
                    Group(name = "Family", isSelected = true),
                    Group(name = "Football team", isSelected = false),
                )
            ),
            viewModel = MapViewModelMock(),
            navController = NavController(LocalContext.current)
        )
    }
}