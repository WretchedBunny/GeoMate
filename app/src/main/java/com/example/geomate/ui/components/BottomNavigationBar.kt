package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geomate.R
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.theme.GeoMateTheme

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    navigateToMap: () -> Unit,
    navigateToGroups: () -> Unit,
    navigateToSocial: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        modifier = modifier,
    ) {
        BottomNavigationBarItem(
            selected = currentRoute == Destinations.MAP_ROUTE,
            onClick = navigateToMap,
            icon = Icons.Outlined.Map,
            label = stringResource(id = R.string.navigation_item_map)
        )
        BottomNavigationBarItem(
            selected = currentRoute == Destinations.GROUPS_ROUTE,
            onClick = navigateToGroups,
            icon = Icons.Outlined.Groups,
            label = stringResource(id = R.string.navigation_item_groups)
        )
        BottomNavigationBarItem(
            selected = currentRoute == Destinations.SOCIAL_ROUTE,
            onClick = navigateToSocial,
            icon = Icons.Outlined.Chat,
            label = stringResource(id = R.string.navigation_item_social)
        )
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
private fun BottomNavigationBarPreview() {
    GeoMateTheme {
        BottomNavigationBar(
            currentRoute = Destinations.MAP_ROUTE,
            navigateToMap = { },
            navigateToGroups = { },
            navigateToSocial = { }
        )
    }
}