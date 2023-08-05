package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geomate.ui.theme.GeoMateTheme

@Composable
fun IconWithNotification(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
    notificationsCount: Int,
    notificationsForegroundColor: Color,
    notificationsBackgroundColor: Color,
) {
    Box(modifier = modifier) {
        Icon(
            imageVector = icon,
            tint = tint,
            contentDescription = contentDescription
        )
        if (notificationsCount > 0) {
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
                        .background(notificationsBackgroundColor)
                )
                Text(
                    text = notificationsCount.toString(),
                    color = notificationsForegroundColor,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
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
private fun IconWithNotificationPreview() {
    GeoMateTheme {
        IconWithNotification(
            icon = Icons.Outlined.Chat,
            tint = MaterialTheme.colorScheme.onBackground,
            notificationsCount = 3,
            notificationsForegroundColor = MaterialTheme.colorScheme.background,
            notificationsBackgroundColor = MaterialTheme.colorScheme.primary
        )
    }
}