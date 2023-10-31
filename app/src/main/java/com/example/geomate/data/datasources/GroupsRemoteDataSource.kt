package com.example.geomate.data.datasources

import com.example.geomate.data.models.Group
import com.example.geomate.ext.snapshotFlow
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

// Question: What does Firebase deserializer wants: DocumentReference or String???

class GroupsRemoteDataSource(private val fireStore: FirebaseFirestore) : GroupsDataSource {
    override suspend fun getAllAsFlow(ownerId: String): Flow<List<Group>> = fireStore
        .collection("groups")
        .whereEqualTo("owner", ownerId)
        .snapshotFlow()
        .map { it.toObjects(Group::class.java) }

    override suspend fun getSingleAsFlow(groupId: String): Flow<Group?> = fireStore
        .collection("groups")
        .whereEqualTo("uid", groupId)
        .get().await()
        .documents.first()
        .reference.snapshotFlow()
        .map { it.toObject(Group::class.java) }

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

    override suspend fun removeUser(groupId: String, userId: String) {
        fireStore.collection("groups")
            .whereEqualTo("uid", groupId)
            .get().await()
            .documents.first().reference
            .update("users", FieldValue.arrayRemove(userId)).await()
    }
}