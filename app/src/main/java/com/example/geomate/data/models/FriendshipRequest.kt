package com.example.geomate.data.models

import java.util.Date

data class FriendshipRequest(
    val status: String = "",
    val senderId: String = "",
    val recipientId: String = "",
    val senderHasNotifications: Boolean = true,
    val recipientHasNotifications: Boolean = true,
    val createdAt: Date = Date(),
)