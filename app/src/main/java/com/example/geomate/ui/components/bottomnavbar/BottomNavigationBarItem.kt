package com.example.geomate.ui.components.bottomnavbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.geomate.ui.components.IconWithNotification

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