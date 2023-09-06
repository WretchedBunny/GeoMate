package com.example.geomate.ui.screens.groups

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.geomate.R
import com.example.geomate.ui.components.BottomNavigationBar
import com.example.geomate.ui.components.GeoMateFAB
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.GroupRow
import com.example.geomate.ui.components.TextFieldIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.screens.map.navigateToMap
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing
import kotlinx.coroutines.launch

fun NavGraphBuilder.groups(
    viewModel: GroupsViewModel,
    navController: NavController,
) {
    composable(Destinations.GROUPS_ROUTE) {
        val uiState by viewModel.uiState.collectAsState()
        GroupsScreen(
            uiState = uiState,
            viewModel = viewModel,
            navController = navController,
        )
    }
}

fun NavController.navigateToGroups() {
    popBackStack()
    navigate(Destinations.GROUPS_ROUTE) {
        launchSingleTop = true
    }
}

@Composable
fun GroupsScreen(
    uiState: GroupsUiState,
    viewModel: GroupsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.fetchProfilePictures(uiState.groupsWithUris)
        }
    }

    Scaffold(
        topBar = {
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
                placeholder = stringResource(id = R.string.groups_search_placeholder),
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            )
        },
        floatingActionButton = {
            GeoMateFAB(
                icon = Icons.Outlined.Add,
                containerColor = MaterialTheme.colorScheme.secondary,
                elevation = 2.dp
            ) { /* TODO: Navigate to group's details screen */ }
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = Destinations.GROUPS_ROUTE,
                navigateToMap = navController::navigateToMap,
                navigateToGroups = { },
                navigateToSocial = { /* navController::navigateToSocial */ },
            )
        },
        modifier = modifier.background(MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            itemsIndexed(uiState.groupsWithUris) { index, groupWithUris ->
                GroupRow(
                    groupWithUris = groupWithUris,
                    onSelect = { /* TODO: Navigate to group details group */ },
                    onRemove = viewModel::removeGroup,
                )
                if (index < uiState.groupsWithUris.lastIndex) {
                    Divider(color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GroupsScreenPreview() {
    GeoMateTheme {
        GroupsScreen(
            uiState = GroupsUiState(),
            viewModel = GroupsViewModelMock(),
            navController = NavController(LocalContext.current)
        )
    }
}