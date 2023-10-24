package com.example.geomate.data.datasources

import com.example.geomate.data.models.FriendshipRequest
import kotlinx.coroutines.flow.Flow


interface NotificationsDataSource {
    suspend fun getFriendshipRequestsFlow(userId: String): Flow<List<FriendshipRequest>>
}