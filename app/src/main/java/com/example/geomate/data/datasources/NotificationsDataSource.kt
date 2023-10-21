package com.example.geomate.data.datasources

import com.example.geomate.data.models.Notification

interface NotificationsDataSource {
    suspend fun getFriendshipRequests(userId: String): List<Notification.FriendshipRequest>
}