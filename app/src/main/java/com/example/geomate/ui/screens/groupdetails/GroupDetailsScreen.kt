package com.example.geomate.ui.screens.groupdetails

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.geomate.R
import com.example.geomate.model.Group
import com.example.geomate.model.GroupWithUris
import com.example.geomate.model.User
import com.example.geomate.ui.components.GeoMateFAB
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.TextFieldIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing

fun NavGraphBuilder.groupDetails() {
    composable("${Destinations.GROUP_DETAILS_ROUTE}/{groupUid}") { backStackEntry ->
        val groupUid = backStackEntry.arguments?.getString("groupUid")
    }
}

fun NavController.navigateToGroupDetails(groupUid: String) {
    navigate("${Destinations.GROUP_DETAILS_ROUTE}/$groupUid") {
        launchSingleTop = true
    }
}

@Composable
fun GroupDetailsScreen(
    uiState: GroupDetailsUiState,
    viewModel: GroupDetailsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
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
            Column {
                GeoMateFAB(
                    icon = Icons.Outlined.Add,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    elevation = 2.dp
                ) { /* TODO: Navigate to "select user" screen */ }

                val icon =
                    if (uiState.groupWithUris.group.users.isEmpty()) Icons.Outlined.ArrowBack
                    else Icons.Outlined.Check
                GeoMateFAB(
                    icon = icon,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    elevation = 2.dp
                ) { /* TODO: Navigate back (and optionally save changes */ }
            }
        },
        modifier = modifier.background(MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            itemsIndexed(uiState.groupWithUris.group.users) { index, user ->
                Text(text = "${user.firstName} ${user.lastName}")
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GroupDetailsScreenPreview() {
    val groupWithUris = GroupWithUris(
        group = Group(
            name = "University",
            users = listOf(
                User(uid = "onYpZ3NG6cSraGnZCSCC4j0JRg73"),
                User(uid = "3fOeWU9LHnTCNf2TxbbCGnbik0F2"),
                User(uid = "tiBC6jmerjbXyv4JdNADqL8BZso1"),
            ),
        )
    )
    GeoMateTheme {
        GroupDetailsScreen(
            uiState = GroupDetailsUiState(groupWithUris = groupWithUris),
            viewModel = GroupDetailsViewModelMock(),
            navController = NavController(LocalContext.current)
        )
    }
}