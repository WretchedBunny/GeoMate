package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.FriendshipRequestDataSource
import com.example.geomate.data.models.FriendshipRequest
import kotlinx.coroutines.flow.Flow

class NotificationRepository(private val friendshipDataSource: FriendshipRequestDataSource) {
    suspend fun getUserSentAsFlow(): Flow<List<FriendshipRequest>> =
        friendshipDataSource.getSentAsFlow()
}