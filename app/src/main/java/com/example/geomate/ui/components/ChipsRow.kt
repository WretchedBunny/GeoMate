package com.example.geomate.ui.components

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.geomate.R
import com.example.geomate.model.Chip
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing

@Composable
fun ChipsRow(
    chips: List<Chip>,
    isAllSelected: Boolean,
    toggleGroup: (Chip) -> Unit,
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
            Chips(
                text = stringResource(id = R.string.map_all),
                isSelected = isAllSelected,
                onClick = { toggleAllGroups(isAllSelected) }
            )
        }
        items(chips) {
            Chips(
                text = it.name,
                isSelected = it.isSelected,
                onClick = { toggleGroup(it) }
            )
        }
        item {
            Chips(
                icon = Icons.Outlined.Add,
                text = stringResource(id = R.string.map_new),
                isSelected = false,
                onClick = { /* TODO: Navigate to "add new group" screen */ }
            )
        }
        item { Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium - MaterialTheme.spacing.small)) }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFF7F0
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF2A2A2A
)
@Composable
private fun ChipsRowPreview() {
    GeoMateTheme {
        ChipsRow(
            chips = listOf(
                Chip(name = "University", isSelected = true),
                Chip(name = "Family", isSelected = true),
                Chip(name = "Football team", isSelected = false),
                Chip(name = "Discord", isSelected = false),
                Chip(name = "Work", isSelected = false),
                Chip(name = "Poker club", isSelected = false),
            ),
            isAllSelected = false,
            toggleGroup = { },
            toggleAllGroups = { },
            navController = NavController(LocalContext.current),
        )
    }
}