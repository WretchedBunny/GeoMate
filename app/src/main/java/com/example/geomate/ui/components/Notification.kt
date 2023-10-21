package com.example.geomate.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.geomate.data.models.Notification

@Composable
fun Notification(notification: Notification, modifier: Modifier = Modifier) {
   when (notification) {
       is Notification.FriendshipRequest -> FriendshipRequest(notification, modifier)
   }
}

@Composable
private fun FriendshipRequest(
    notification: Notification.FriendshipRequest,
    modifier: Modifier = Modifier
) {
    // TODO: Proper friendship request notification view
    Row(modifier = modifier) {
        Text(notification.senderId)
    }
}