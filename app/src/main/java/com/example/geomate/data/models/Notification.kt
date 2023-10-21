package com.example.geomate.data.models

import java.time.LocalDateTime
import java.util.Date

sealed class Notification {
    data class FriendshipRequest(
        val senderId: String = "",
        val createdAt: Date = Date(),
    ) : Notification() {
        companion object {
            fun from(
                friendshipRequest: com.example.geomate.data.models.FriendshipRequest
            ): FriendshipRequest = FriendshipRequest(
                senderId = friendshipRequest.senderId,
                createdAt = friendshipRequest.createdAt,
            )
        }
    }
}