package com.example.geomate.data.datasources

import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.FriendshipStatus
import kotlinx.coroutines.flow.Flow

interface FriendshipRequestDataSource {
    suspend fun getFlow(userId: String): Flow<FriendshipRequest?>
    suspend fun get(userId: String): FriendshipRequest?
    suspend fun getSentListFlow(userId: String): Flow<List<FriendshipRequest>>
    suspend fun add(friendshipRequest: FriendshipRequest)
    suspend fun remove(userId: String)
    suspend fun updateStatus(userId: String, status: FriendshipStatus)
    suspend fun updateNotifications(userId: String, isSender: Boolean, value: Boolean)
}