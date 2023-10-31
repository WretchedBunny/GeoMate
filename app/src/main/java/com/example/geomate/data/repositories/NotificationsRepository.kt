package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.NotificationsDataSource
import com.example.geomate.data.models.FriendshipRequest
import kotlinx.coroutines.flow.Flow

class NotificationsRepository(
    private val notificationsDataSource: NotificationsDataSource
) {
    suspend fun getFriendshipRequestsFlow(userId: String): Flow<List<FriendshipRequest>> {
        return notificationsDataSource.getFriendshipRequestsFlow(userId)
    }
}