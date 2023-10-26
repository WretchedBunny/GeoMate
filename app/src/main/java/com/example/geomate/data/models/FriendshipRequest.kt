package com.example.geomate.data.models

import java.util.Date

enum class FriendshipStatus {
    Sent,
    Accepted,
}

data class FriendshipRequest(
    val createdAt: Date = Date(),
    val senderHasNotifications: Boolean = false,
    val recipientHasNotifications: Boolean = false,
    val senderId: String = "",
    val recipientId: String = "",
    val status: FriendshipStatus = FriendshipStatus.Sent,
)