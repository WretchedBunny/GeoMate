package com.example.geomate.ui.screens.search

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.geomate.R
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.SearchUserRow
import com.example.geomate.ui.components.TextFieldIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.screens.profile.navigateToProfile
import com.example.geomate.ui.theme.spacing

fun NavGraphBuilder.search(
    viewModel: SearchViewModel,
    navController: NavController
) {
    composable(Destinations.SEARCH_ROUTE) {
        val searchQuery by viewModel.searchQuery.collectAsState()
        val uiState by viewModel.uiState.collectAsState()
        SearchScreen(
            searchQuery = searchQuery,
            uiState = uiState,
            viewModel = viewModel,
            navController = navController
        )
    }
}

fun NavController.navigateToSearch() {
    navigate(Destinations.SEARCH_ROUTE) {
        launchSingleTop = true
    }
}

@Composable
fun SearchScreen(
    searchQuery: String,
    uiState: SearchUiState,
    viewModel: SearchViewModel,
    navController: NavController,
) {
    val searchFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
        searchFocusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = MaterialTheme.spacing.medium)
        ) {
            GeoMateTextField(
                value = searchQuery,
                onValueChange = viewModel::updateSearchQuery,
                leadingIcons = listOf(
                    TextFieldIcon(onClick = navController::navigateUp) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            modifier = it,
                        )
                    }
                ),
                placeholder = stringResource(id = R.string.users_search_placeholder),
                imeAction = ImeAction.Search,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .focusRequester(searchFocusRequester)
            )

            if (uiState.isLoading) {
                CircularProgressIndicator()
//            } else if (searchQuery.isBlank()) {
//                Text(
//                    text = "Start searching",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.onBackground
//                )
//            } else if (uiState.users.isEmpty()) {
//                Text(
//                    text = "No one found :(",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.onBackground
//                )
            } else {
                LazyColumn {
                    itemsIndexed(uiState.users.keys.toList()) { index, user ->
                        SearchUserRow(
                            user = Pair(user, uiState.users[user] ?: Uri.EMPTY),
                            onSelect = { navController.navigateToProfile(it.uid) }
                        )
                        if (index < uiState.users.size - 1) {
                            Divider(color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
        }
    }
}