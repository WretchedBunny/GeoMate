package com.example.geomate.data.datasources

import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.FriendshipStatus
import com.example.geomate.ext.snapshotFlow
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FriendshipRequestRemoteDataSource(private val fireStore: FirebaseFirestore) :
    FriendshipRequestDataSource {
    private fun documentSearchFilter(userId: String): Filter {
        return Filter.and(
            Filter.or(
                Filter.equalTo("recipientId", Firebase.auth.currentUser?.uid),
                Filter.equalTo("senderId", Firebase.auth.currentUser?.uid),
            ),
            Filter.or(
                Filter.equalTo("recipientId", userId),
                Filter.equalTo("senderId", userId),
            )
        )
    }

    override suspend fun getFlow(userId: String): Flow<FriendshipRequest?> {
        return fireStore.collection("friendshipRequests").where(documentSearchFilter(userId))
            .snapshotFlow().map { it.toObjects(FriendshipRequest::class.java).firstOrNull() }
    }

    override suspend fun get(userId: String): FriendshipRequest? {
        return fireStore.collection("friendshipRequests").where(documentSearchFilter(userId)).get()
            .await().documents.map { it.toObject(FriendshipRequest::class.java) }.firstOrNull()
    }

    override suspend fun add(friendshipRequest: FriendshipRequest) {
        fireStore.collection("friendshipRequests").add(friendshipRequest).await()
    }

    override suspend fun remove(userId: String) {
        fireStore.collection("friendshipRequests").where(documentSearchFilter(userId))
            .get().await().documents.firstOrNull()?.reference?.delete()
    }

    override suspend fun updateStatus(userId: String, status: FriendshipStatus) {
        fireStore.collection("friendshipRequests").where(documentSearchFilter(userId))
            .get().await().documents.firstOrNull()?.reference?.update(
                "status",
                FriendshipStatus.Accepted
            )
    }

    override suspend fun updateNotifications(userId: String, isSender: Boolean, value: Boolean) {
        val notificationsField =
            if (isSender) "senderHasNotifications" else "recipientHasNotifications"

        fireStore.collection("friendshipRequests").where(documentSearchFilter(userId))
            .get().await().documents.firstOrNull()?.reference?.update(notificationsField, value)

    }
}