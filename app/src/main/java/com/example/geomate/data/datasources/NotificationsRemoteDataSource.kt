package com.example.geomate.data.datasources

import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.ext.snapshotFlow
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotificationsRemoteDataSource(
    private val fireStore: FirebaseFirestore,
) : NotificationsDataSource {
    override suspend fun getFriendshipRequestsFlow(userId: String): Flow<List<FriendshipRequest>> = fireStore
        .collection("friendshipRequests")
        .whereEqualTo("recipientId", userId)
        .whereEqualTo("status", "Sent")
        .orderBy("createdAt", Query.Direction.DESCENDING)
        .snapshotFlow()
        .map { it.toObjects(FriendshipRequest::class.java) }
}