package com.example.geomate.ui.screens.notifications

import com.example.geomate.data.models.Notification

data class NotificationsUiState(
    val notifications: List<Notification> = listOf(),
    val isLoading: Boolean = true
)