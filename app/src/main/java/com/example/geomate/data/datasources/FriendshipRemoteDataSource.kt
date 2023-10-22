package com.example.geomate.data.datasources

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class FriendshipRemoteDataSource(private val fireStore: FirebaseFirestore) : FriendshipDataSource {
    override suspend fun get(userId: String): QuerySnapshot {
//        return fireStore.collection("friendshipRequests").whereEqualTo("recipientId", userId)
//            .whereEqualTo("senderId", userId).get().await()
        return fireStore.collection("friendshipRequests").where(
            Filter.or(
                Filter.equalTo("recipientId", userId),
                Filter.equalTo("senderId", userId),
            )
        ).get().await()
    }
}