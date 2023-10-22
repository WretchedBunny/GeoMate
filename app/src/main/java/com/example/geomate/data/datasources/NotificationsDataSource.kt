package com.example.geomate.data.datasources

import com.example.geomate.data.models.FriendshipRequest


interface NotificationsDataSource {
    // TODO: Rewrite using Flow
    suspend fun getFriendshipRequests(userId: String): List<FriendshipRequest>
}