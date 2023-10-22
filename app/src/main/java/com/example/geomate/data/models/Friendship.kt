package com.example.geomate.data.models

import com.google.firebase.Timestamp

enum class FriendshipStatus {
    None,
    SentByMe,
    SentByUser,
    AcceptedWithNotifications,
    AcceptedWithoutNotifications

}

data class Friendship(
    val createdAt: Timestamp = Timestamp.now(),
    val senderHasNotifications: Boolean = false,
    val recipientHasNotifications: Boolean = false,
    val senderId: String = "",
    val recipientId: String = "",
    val status: FriendshipStatus = FriendshipStatus.None,
)