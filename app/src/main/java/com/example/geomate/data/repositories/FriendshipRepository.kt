package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.FriendshipRequestRemoteDataSource
import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.FriendshipStatus
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

class FriendshipRepository(private val friendshipDataSource: FriendshipRequestRemoteDataSource) {
    private val friendshipRequestFlows: MutableMap<String, Flow<FriendshipRequest?>> =
        mutableMapOf()
    private val friendshipRequests: MutableMap<String, FriendshipRequest?> = mutableMapOf()

    suspend fun getFlow(userId: String): Flow<FriendshipRequest?> {
        return friendshipRequestFlows[userId] ?: run {
            val friendship = friendshipDataSource.getFlow(userId)
            friendshipRequestFlows[userId] = friendship
            return friendship
        }
    }

    suspend fun get(userId: String): FriendshipRequest? {
        return friendshipRequests[userId] ?: run {
            val friendshipRequest = friendshipDataSource.get(userId)
            friendshipRequests[userId] = friendshipRequest
            return friendshipRequest
        }
    }

    suspend fun add(friendshipRequest: FriendshipRequest) =
        friendshipDataSource.add(friendshipRequest)

    suspend fun remove(userId: String) = friendshipDataSource.remove(userId)

    suspend fun updateStatus(userId: String, status: FriendshipStatus) =
        friendshipDataSource.updateStatus(userId, status)

    suspend fun updateCurrentUserNotifications(userId: String, value: Boolean) {
        get(userId).let { request ->
            (request?.senderId == Firebase.auth.currentUser?.uid).also { isSender ->
                friendshipDataSource.updateNotifications(userId, isSender, value)
            }
        }
    }

}