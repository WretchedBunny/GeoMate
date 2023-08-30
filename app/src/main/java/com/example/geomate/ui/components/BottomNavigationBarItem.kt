package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geomate.R
import com.example.geomate.ui.theme.GeoMateTheme

@Composable
fun RowScope.BottomNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
    notificationsCount: Int = 0,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            val (textColor, backgroundColor) = when (selected) {
                true -> Pair(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.background
                )

                false -> Pair(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.primary
                )
            }
            IconWithNotification(
                icon = icon,
                notificationsCount = notificationsCount,
                notificationsForegroundColor = textColor,
                notificationsBackgroundColor = backgroundColor
            )
        },
        colors = NavigationBarItemDefaults.colors(
            unselectedIconColor = MaterialTheme.colorScheme.onBackground,
            unselectedTextColor = MaterialTheme.colorScheme.onBackground,
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.onBackground,
            indicatorColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    )
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
private fun MapSelectedBottomNavigationBarItemPreview() {
    GeoMateTheme {
        Row(
            modifier = Modifier
                .width(100.dp)
                .height(80.dp)
        ) {
            BottomNavigationBarItem(
                selected = true,
                onClick = { },
                icon = Icons.Outlined.Map,
                label = stringResource(id = R.string.navigation_item_map)
            )
        }
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
private fun SocialNotSelectedBottomNavigationBarItemPreview() {
    GeoMateTheme {
        Row(
            modifier = Modifier
                .width(100.dp)
                .height(80.dp)
        ) {
            BottomNavigationBarItem(
                selected = false,
                onClick = { },
                icon = Icons.Outlined.Chat,
                label = stringResource(id = R.string.navigation_item_social),
                notificationsCount = 3
            )
        }
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
private fun SocialSelectedBottomNavigationBarItemPreview() {
    GeoMateTheme {
        Row(
            modifier = Modifier
                .width(100.dp)
                .height(80.dp)
        ) {
            BottomNavigationBarItem(
                selected = true,
                onClick = { },
                icon = Icons.Outlined.Chat,
                label = stringResource(id = R.string.navigation_item_social),
                notificationsCount = 3
            )
        }
    }
}