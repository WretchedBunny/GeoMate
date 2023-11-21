package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.FriendshipRequestDataSource
import com.example.geomate.data.datasources.GroupsDataSource
import com.example.geomate.data.datasources.UsersDataSource
import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.FriendshipStatus
import com.example.geomate.data.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class FriendshipRepository(
    private val friendshipDataSource: FriendshipRequestDataSource,
    private val groupsDataSource: GroupsDataSource,
    private val usersDataSource: UsersDataSource,
) {
    private val friendshipRequestFlows = mutableMapOf<String, Flow<FriendshipRequest?>>()
    private val friendshipRequests = mutableMapOf<String, FriendshipRequest?>()

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

    suspend fun getFriendsAsFlow(): Flow<List<User>> = friendshipDataSource.getAllAcceptedAsFlow()
        .transform { requests ->
            val usersIds = requests.map {
                if (it.senderId != Firebase.auth.uid) it.senderId else it.recipientId
            }
            emit(usersDataSource.getAll(usersIds))
        }

    suspend fun getFriendsNotFromGroupAsFlow(groupId: String): Flow<List<User>> {
        return friendshipDataSource.getAllAcceptedAsFlow().transform { requests ->
            val friendsIds = requests.map { request ->
                if (request.senderId != Firebase.auth.uid) request.senderId else request.recipientId
            }.toSet()
            val membersIds = groupsDataSource.getUsersIds(groupId).toSet()

            emit(usersDataSource.getAll(friendsIds.minus(membersIds).toList()))
        }
    }

    suspend fun getFriends(): List<User> {
        val usersIds = friendshipDataSource.getAllAccepted().map {
            if (it.senderId != Firebase.auth.uid) it.senderId else it.recipientId
        }
        return usersDataSource.getAll(usersIds)
    }

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