package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.FriendshipRequestDataSource
import com.example.geomate.data.datasources.UsersDataSource
import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.FriendshipStatus
import com.example.geomate.data.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

class FriendshipRepository(
    private val friendshipDataSource: FriendshipRequestDataSource,
    private val usersDataSource: UsersDataSource,
) {
    private val friendshipRequestFlows: MutableMap<String, Flow<FriendshipRequest?>> =
        mutableMapOf()
    private val friendshipRequests: MutableMap<String, FriendshipRequest?> = mutableMapOf()

    suspend fun getAsFlow(userId: String): Flow<FriendshipRequest?> {
        return friendshipRequestFlows[userId] ?: run {
            val friendship = friendshipDataSource.getSingleAsFlow(userId)
            friendshipRequestFlows[userId] = friendship
            return friendship
        }
    }

    suspend fun getSingle(userId: String): FriendshipRequest? {
        return friendshipRequests[userId] ?: run {
            val friendshipRequest = friendshipDataSource.getSingle(userId)
            friendshipRequests[userId] = friendshipRequest
            return friendshipRequest
        }
    }

    suspend fun getFriendsAsFlow(): Flow<List<User>> =
        friendshipDataSource.getAllAccepted()
            .map {
                if (it.senderId != Firebase.auth.uid) it.senderId else it.recipientId
            }
            .run { usersDataSource.getAllAsFlow(this) }

    suspend fun getFriends(): List<User> =
        friendshipDataSource.getAllAccepted()
            .map {
                if (it.senderId != Firebase.auth.uid) it.senderId else it.recipientId
            }
            .run { usersDataSource.getAll(this) }

    suspend fun add(friendshipRequest: FriendshipRequest) =
        friendshipDataSource.add(friendshipRequest)

    suspend fun remove(userId: String) = friendshipDataSource.remove(userId)

    suspend fun updateStatus(userId: String, status: FriendshipStatus) =
        friendshipDataSource.updateStatus(userId, status)

    suspend fun updateCurrentUserNotifications(userId: String, value: Boolean) {
        getSingle(userId).let { request ->
            (request?.senderId == Firebase.auth.currentUser?.uid).also { isSender ->
                friendshipDataSource.updateNotifications(userId, isSender, value)
            }
        }
    }

}