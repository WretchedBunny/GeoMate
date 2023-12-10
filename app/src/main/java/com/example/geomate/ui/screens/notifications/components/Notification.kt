package com.example.geomate.ui.screens.notifications.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.geomate.R
import com.example.geomate.data.models.Notification
import com.example.geomate.ui.components.GeomateAlertDialog
import com.example.geomate.ui.screens.notifications.NotificationsViewModel
import com.example.geomate.ui.screens.profile.navigateToProfile
import com.example.geomate.ui.theme.spacing
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.fresco.FrescoImage

@Composable
fun Notification(
    notification: Notification,
    viewModel: ViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    when (notification) {
        is Notification.FriendshipRequest -> FriendshipRequest(
            notification,
            { (viewModel as NotificationsViewModel).acceptRequest(notification.sender.uid) },
            { (viewModel as NotificationsViewModel).declineRequest(notification.sender.uid) },
            navController,
            modifier
        )
    }
}

@Composable
private fun FriendshipRequest(
    notification: Notification.FriendshipRequest,
    acceptRequest: () -> Unit,
    declineRequest: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { navController.navigateToProfile(notification.sender.uid) }
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
    ) {
        val drawableId =
            if (isSystemInDarkTheme()) R.drawable.profile_picture_placeholder_dark
            else R.drawable.profile_picture_placeholder_light
        var isAlertDialogVisible by remember { mutableStateOf(false) }

        FrescoImage(
            imageUrl = notification.senderProfilePicture.toString(),
            failure = {
                Image(painter = painterResource(id = drawableId), contentDescription = null)
            },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            modifier = modifier
                .size(60.dp)
                .clip(CircleShape)
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("${notification.sender.firstName} ${notification.sender.lastName} ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                    append(stringResource(id = R.string.notifications_friendship_request))
                }
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
            IconButton(
                onClick = { acceptRequest() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {
                    isAlertDialogVisible = true
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null
                )
            }
        }
        if (isAlertDialogVisible) {
            GeomateAlertDialog(
                onDismissRequest = {
                    isAlertDialogVisible = false
                },
                onConfirmation = {
                    // Handle confirmation action
                    isAlertDialogVisible = false
                    declineRequest()
                },
                dialogTitle = stringResource(id = R.string.alert_dialog_confirmation_title),
                dialogText = stringResource(id = R.string.alert_dialog_confirmation_body)
            )
        }
    }
}
