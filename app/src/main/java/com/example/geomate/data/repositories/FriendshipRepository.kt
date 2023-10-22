package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.FriendshipRemoteDataSource
import com.example.geomate.data.models.Friendship

class FriendshipRepository(private val friendshipDataSource: FriendshipRemoteDataSource) {
    suspend fun get(userId: String): Friendship {
        return friendshipDataSource.get(userId)
            .documents
            .firstOrNull()
            ?.toObject(Friendship::class.java)
            ?: Friendship()
    }
}