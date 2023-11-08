package com.example.geomate.data.datasources

import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.FriendshipStatus
import kotlinx.coroutines.flow.Flow

interface FriendshipRequestDataSource {
    suspend fun getSingleAsFlow(userId: String): Flow<FriendshipRequest?>
    suspend fun getSingle(userId: String): FriendshipRequest?
    suspend fun getSentAsFlow(): Flow<List<FriendshipRequest>>
    suspend fun getAllAccepted(): List<FriendshipRequest>

    suspend fun add(friendshipRequest: FriendshipRequest)
    suspend fun remove(userId: String)
    suspend fun updateStatus(userId: String, status: FriendshipStatus)
    suspend fun updateNotifications(userId: String, isSender: Boolean, value: Boolean)
}