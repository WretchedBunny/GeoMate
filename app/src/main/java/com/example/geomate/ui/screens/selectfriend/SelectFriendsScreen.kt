package com.example.geomate.ui.screens.selectfriend

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.geomate.R
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.TextFieldIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.screens.profile.navigateToProfile
import com.example.geomate.ui.screens.selectfriend.components.Friend
import com.example.geomate.ui.theme.spacing

fun NavGraphBuilder.selectFriend(
    viewModel: SelectFriendViewModel,
    navController: NavController
) {
    composable(
        "${Destinations.GROUP_SELECT_FRIEND_ROUTE}?groupId={groupId}",
        arguments = listOf(navArgument("groupId") {
            type = NavType.StringType
            defaultValue = ""
        })
    ) { backStackEntry ->
        val uiState by viewModel.uiState.collectAsState()
        SelectFriendsScreen(
            groupId = backStackEntry.arguments?.getString("groupId") ?: "",
            uiState = uiState,
            viewModel = viewModel,
            navController = navController,
        )
    }
}

fun NavController.navigateToSelectFriend(groupId: String) {
    val route = when(groupId.isEmpty()) {
        true -> Destinations.GROUP_SELECT_FRIEND_ROUTE
        false -> "${Destinations.GROUP_SELECT_FRIEND_ROUTE}?groupId=$groupId"
    }
    navigate(route) {
        launchSingleTop = true
    }
}

@Composable
fun SelectFriendsScreen(
    groupId: String,
    uiState: FriendsUiState,
    viewModel: SelectFriendViewModel,
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchFriends(groupId)
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
                value = uiState.searchQuery,
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
                placeholder = stringResource(id = R.string.friends_search_placeholder),
                imeAction = ImeAction.Search,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
            )

            if (uiState.isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    itemsIndexed(uiState.matchedFriends) { index, friend ->
                        Friend(
                            friend = friend,
                            profilePicture = uiState.friends[friend] ?: Uri.EMPTY,
                            onSelect = { navController.navigateToProfile(friend.uid) },
                            onAdd = viewModel::addFriendToGroup
                        )
                        if (index < uiState.friends.size - 1) {
                            Divider(color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
        }
    }
}