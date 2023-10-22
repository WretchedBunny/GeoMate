package com.example.geomate.data.datasources

import com.example.geomate.data.models.FriendshipRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class NotificationsRemoteDataSource(
    private val fireStore: FirebaseFirestore,
) : NotificationsDataSource {
    override suspend fun getFriendshipRequests(userId: String): List<FriendshipRequest> = fireStore
        .collection("friendshipRequests")
        .whereEqualTo("recipientId", userId)
        .whereEqualTo("status", "sent")
        .orderBy("createdAt", Query.Direction.DESCENDING)
        .get().await().toObjects(FriendshipRequest::class.java)
}