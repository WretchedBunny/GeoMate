package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.FriendshipRemoteDataSource
import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.FriendshipStatus
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class FriendshipRepository(private val friendshipDataSource: FriendshipRemoteDataSource) {
    private val friendshipRequestFlows: MutableMap<String, Flow<FriendshipRequest?>> =
        mutableMapOf()

    suspend fun get(userId: String): Flow<FriendshipRequest?> {
        return friendshipRequestFlows[userId] ?: run {
            val friendship = friendshipDataSource.get(userId)
            friendshipRequestFlows[userId] = friendship
            return friendship
        }
    }

    suspend fun add(friendshipRequest: FriendshipRequest) =
        friendshipDataSource.add(friendshipRequest)

    suspend fun remove(userId: String) = friendshipDataSource.remove(userId)
    suspend fun updateStatus(userId: String, status: FriendshipStatus) =
        friendshipDataSource.updateStatus(userId, status)

    suspend fun updateCurrentUserNotifications(
        userId: String,
        value: Boolean,
    ) {
        val isSenderNotifications = friendshipRequestFlows[userId]?.firstOrNull()?.let {
            when (it.senderId) {
                Firebase.auth.currentUser?.uid -> true
                else -> false
            }
        }
        isSenderNotifications?.let {
            friendshipDataSource.updateNotifications(userId, it, value)
        }
    }

}