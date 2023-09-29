package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.GroupsDataSource
import com.example.geomate.data.models.Group
import kotlinx.coroutines.flow.Flow

class GroupsRepository(private val groupsDataSource: GroupsDataSource) {
    suspend fun getAll(ownerId: String): Flow<List<Group>> = groupsDataSource.getAll(ownerId)
    suspend fun get(groupId: String): Flow<Group?> = groupsDataSource.get(groupId)
    suspend fun remove(group: Group) = groupsDataSource.remove(group)
    suspend fun add(group: Group) = groupsDataSource.add(group)
    suspend fun removeUser(groupId: String, userId: String) = groupsDataSource.removeUser(groupId, userId)
}