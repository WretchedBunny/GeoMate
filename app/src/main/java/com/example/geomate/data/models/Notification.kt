package com.example.geomate.data.models

import android.net.Uri
import java.util.Date

sealed class Notification {
    data class FriendshipRequest(
        val sender: User = User(),
        val senderProfilePicture: Uri = Uri.EMPTY,
        val createdAt: Date = Date(),
    ) : Notification()
}