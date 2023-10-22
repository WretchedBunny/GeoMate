package com.example.geomate.data.datasources

import com.google.firebase.firestore.QuerySnapshot

interface FriendshipDataSource {
    suspend fun get(userId: String): QuerySnapshot?
}