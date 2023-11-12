package com.example.geomate.ui.screens.profile.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.geomate.R
import com.example.geomate.statemachine.FriendshipState
import com.example.geomate.ui.components.ButtonType
import com.example.geomate.ui.components.GeoMateButtonWithIcon
import com.example.geomate.ui.components.GeomateAlertDialog
import com.example.geomate.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun Buttons(
    userId: String,
    friendshipState: FriendshipState,
    modifier: Modifier = Modifier,
) {
    var isAlertDialogVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = modifier
    ) {
        when (friendshipState) {
            is FriendshipState.None -> {
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_send_request),
                    icon = Icons.Outlined.Add,
                    onClick = {
                        coroutineScope.launch { friendshipState.send(userId) }
                    },
                    type = ButtonType.Primary,
                )
            }

            is FriendshipState.SentByMe -> {
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_revoke_request),
                    icon = Icons.Outlined.Undo,
                    onClick = {
                        coroutineScope.launch { friendshipState.revoke(userId) }
                    },
                    type = ButtonType.Secondary,
                )
            }

            is FriendshipState.SentByUser -> {
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_accept),
                    icon = Icons.Outlined.Check,
                    onClick = {
                        coroutineScope.launch { friendshipState.accept(userId) }
                    },
                    type = ButtonType.Primary,
                )
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_decline),
                    icon = Icons.Outlined.Close,
                    onClick = {
                        isAlertDialogVisible = true
                    },
                    type = ButtonType.Secondary,
                )
            }

            is FriendshipState.AcceptedWithNotifications -> {
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_turn_off_notifications),
                    icon = Icons.Outlined.LocationOff,
                    onClick = {
                        coroutineScope.launch { friendshipState.turnOffNotifications(userId) }
                    },
                    type = ButtonType.Secondary,
                )
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_remove),
                    icon = Icons.Outlined.Close,
                    onClick = {
                        coroutineScope.launch { friendshipState.remove(userId) }
                    },
                    type = ButtonType.Secondary,
                )
            }

            is FriendshipState.AcceptedWithoutNotifications -> {
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_turn_on_notifications),
                    icon = Icons.Outlined.LocationOn,
                    onClick = {
                        coroutineScope.launch { friendshipState.turnOnNotifications(userId) }
                    },
                    type = ButtonType.Secondary,
                )
                GeoMateButtonWithIcon(
                    text = stringResource(id = R.string.button_remove),
                    icon = Icons.Outlined.Close,
                    onClick = {
                        coroutineScope.launch { friendshipState.remove(userId) }
                    },
                    type = ButtonType.Secondary,
                )
            }
        }
        if (isAlertDialogVisible) {
            GeomateAlertDialog(
                onDismissRequest = {
                    isAlertDialogVisible = false
                },
                onConfirmation = {
                    isAlertDialogVisible = false
                    when (friendshipState) {
                        is FriendshipState.AcceptedWithNotifications -> {
                            coroutineScope.launch { friendshipState.remove(userId) }
                        }

                        is FriendshipState.AcceptedWithoutNotifications -> {
                            coroutineScope.launch { friendshipState.remove(userId) }
                        }

                        is FriendshipState.SentByUser -> {
                            coroutineScope.launch { friendshipState.decline(userId) }
                        }

                        else -> {}
                    }
                },
                dialogTitle = stringResource(R.string.alert_dialog_confirmation_title),
                dialogText = stringResource(R.string.alert_dialog_confirmation_body)
            )
        }
    }
}