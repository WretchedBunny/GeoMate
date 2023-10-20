package com.example.geomate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.LocationOff
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Undo
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.geomate.R
import com.example.geomate.statemachine.FriendshipState
import com.example.geomate.ui.theme.spacing

@Composable
fun ProfileButtonsRow(
    friendshipState: FriendshipState,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = modifier
    ) {
        when (friendshipState) {
            is FriendshipState.None -> {
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_send_request),
                    icon = Icons.Outlined.Add,
                    onClick = friendshipState::send,
                    type = ButtonType.Primary,
                )
            }
            is FriendshipState.SentByMe -> {
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_revoke_request),
                    icon = Icons.Outlined.Undo,
                    onClick = friendshipState::revoke,
                    type = ButtonType.Secondary,
                )
            }
            is FriendshipState.SentByUser -> {
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_accept),
                    icon = Icons.Outlined.Check,
                    onClick = friendshipState::accept,
                    type = ButtonType.Primary,
                )
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_decline),
                    icon = Icons.Outlined.Close,
                    onClick = friendshipState::decline,
                    type = ButtonType.Secondary,
                )
            }
            is FriendshipState.AcceptedWithNotifications ->  {
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_turn_off_notifications),
                    icon = Icons.Outlined.LocationOff,
                    onClick = friendshipState::turnOffNotifications,
                    type = ButtonType.Secondary,
                )
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_remove),
                    icon = Icons.Outlined.Close,
                    onClick = friendshipState::remove,
                    type = ButtonType.Secondary,
                )
            }
            is FriendshipState.AcceptedWithoutNotifications -> {
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_turn_on_notifications),
                    icon = Icons.Outlined.LocationOn,
                    onClick = friendshipState::turnOnNotifications,
                    type = ButtonType.Secondary,
                )
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_remove),
                    icon = Icons.Outlined.Close,
                    onClick = friendshipState::remove,
                    type = ButtonType.Secondary,
                )
            }
        }
    }
}