package com.example.geomate.data.datasources

import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.FriendshipStatus
import kotlinx.coroutines.flow.Flow

interface FriendshipDataSource {
    suspend fun get(userId: String): Flow<FriendshipRequest?>
    suspend fun add(friendshipRequest: FriendshipRequest)
    suspend fun remove(userId: String)
    suspend fun updateStatus(userId: String, status: FriendshipStatus)
    suspend fun updateNotifications(userId: String, isSender: Boolean, value: Boolean)
}