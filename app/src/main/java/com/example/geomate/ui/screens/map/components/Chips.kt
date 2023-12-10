package com.example.geomate.ui.screens.map.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.geomate.R
import com.example.geomate.data.models.Group
import com.example.geomate.ui.screens.groupdetails.navigateToGroupDetails
import com.example.geomate.ui.theme.spacing

@Composable
fun Chips(
    chips: Map<Group, Boolean>,
    isAllSelected: Boolean,
    toggleGroup: (Group) -> Unit,
    toggleAllGroups: (Boolean) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = modifier
    ) {
        item { Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium - MaterialTheme.spacing.small)) }
        item {
            Chip(
                text = stringResource(id = R.string.map_all),
                isSelected = isAllSelected,
                onClick = { toggleAllGroups(isAllSelected) }
            )
        }
        items(chips.keys.toList()) {
            Chip(
                text = it.name,
                isSelected = chips[it] ?: false,
                onClick = { toggleGroup(it) }
            )
        }
        item {
            Chip(
                icon = Icons.Outlined.Add,
                text = stringResource(id = R.string.map_new),
                isSelected = false,
                onClick = { navController.navigateToGroupDetails("") }
            )
        }
        item { Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium - MaterialTheme.spacing.small)) }
    }
}