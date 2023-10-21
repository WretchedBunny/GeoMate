package com.example.geomate.data.datasources

import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.Notification
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NotificationsRemoteDataSource(
    private val fireStore: FirebaseFirestore,
) : NotificationsDataSource {
    override suspend fun getFriendshipRequests(userId: String): List<Notification.FriendshipRequest> {
        return fireStore.collection("friendshipRequests")
            .whereEqualTo("recipientId", userId)
            .whereEqualTo("status", "sent")
            .get().await().toObjects(FriendshipRequest::class.java)
            .map { Notification.FriendshipRequest.from(it) }
    }
}