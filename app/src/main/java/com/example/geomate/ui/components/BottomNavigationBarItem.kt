package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    notifications: Int? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Box {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
                notifications?.let {
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
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (-3).dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(backgroundColor)
                        )
                        Text(
                            text = it.toString(),
                            color = textColor,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
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
                notifications = 3
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
                notifications = 3
            )
        }
    }
}