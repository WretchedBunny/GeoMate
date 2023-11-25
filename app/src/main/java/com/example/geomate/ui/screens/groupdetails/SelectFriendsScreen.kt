package com.example.geomate.ui.screens.groupdetails

import android.net.Uri
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.example.geomate.R
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.TextFieldIcon
import com.example.geomate.ui.screens.groupdetails.components.Friend
import com.example.geomate.ui.screens.profile.navigateToProfile
import com.example.geomate.ui.theme.spacing

@Composable
internal fun SelectFriendsScreen(
    uiState: SelectFriendUiState,
    viewModel: GroupDetailsViewModel,
    navController: NavController,
) {
    BackHandler {
        viewModel.toggleSelectFriendVisibility()
    }

    LaunchedEffect(Unit) {
        viewModel.fetchFriends()
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
                    TextFieldIcon(onClick = viewModel::toggleSelectFriendVisibility) {
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
                            onAdd = { user ->
                                viewModel.addUser(user, uiState.friends[user] ?: Uri.EMPTY)
                                viewModel.toggleSelectFriendVisibility()
                            },
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