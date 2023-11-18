package com.example.geomate.data.datasources

import com.example.geomate.data.models.Group
import kotlinx.coroutines.flow.Flow

interface GroupsDataSource {
    suspend fun getAllAsFlow(ownerId: String): Flow<List<Group>>
    suspend fun getSingleAsFlow(groupId: String): Flow<Group?>

    suspend fun add(group: Group)
    suspend fun update(groupId: String, name: String, users: List<String>)
    suspend fun remove(group: Group)
}