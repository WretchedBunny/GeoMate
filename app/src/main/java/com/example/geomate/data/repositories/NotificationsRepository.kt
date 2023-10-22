package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.NotificationsDataSource
import com.example.geomate.data.models.FriendshipRequest

class NotificationsRepository(
    private val notificationsDataSource: NotificationsDataSource
) {
    suspend fun getFriendshipRequests(userId: String): List<FriendshipRequest> {
        return notificationsDataSource.getFriendshipRequests(userId)
    }
}