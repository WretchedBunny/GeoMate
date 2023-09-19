package com.example.geomate.data.datasources

import com.example.geomate.data.models.Group
import com.example.geomate.ext.snapshotFlow
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class GroupsRemoteDataSource(private val fireStore: FirebaseFirestore) : GroupsDataSource {
    override suspend fun getAll(ownerId: String): Flow<List<Group>> = fireStore
        .collection("groups")
        .whereEqualTo("owner", ownerId)
        .snapshotFlow()
        .map { it.toObjects(Group::class.java) }

    override suspend fun add(group: Group) {
        fireStore.collection("groups")
            .add(group).await()
    }

    override suspend fun remove(group: Group) {
        fireStore.collection("groups")
            .whereEqualTo("uid", group.uid)
            .get().await()
            .documents.first().reference.delete()
    }
}