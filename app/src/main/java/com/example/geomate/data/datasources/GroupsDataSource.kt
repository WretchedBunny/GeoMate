package com.example.geomate.data.datasources

import com.example.geomate.data.models.Group
import kotlinx.coroutines.flow.Flow

interface GroupsDataSource {
    suspend fun getAllAsFlow(ownerId: String): Flow<List<Group>>
    suspend fun getSingleAsFlow(groupId: String): Flow<Group?>

    suspend fun add(group: Group)
    suspend fun remove(group: Group)
    suspend fun removeUser(groupId: String, userId: String)
}