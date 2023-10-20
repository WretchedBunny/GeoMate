package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.GroupsDataSource
import com.example.geomate.data.models.Group
import kotlinx.coroutines.flow.Flow

class GroupsRepository(private val groupsDataSource: GroupsDataSource) {
    private val allGroupsFlows: MutableMap<String, Flow<List<Group>>> = mutableMapOf()
    private val groupFlows: MutableMap<String, Flow<Group?>> = mutableMapOf()

    suspend fun getAll(ownerId: String): Flow<List<Group>> {
        return allGroupsFlows[ownerId] ?: run {
            val groups = groupsDataSource.getAll(ownerId)
            allGroupsFlows[ownerId] = groups
            return groups
        }
    }
    suspend fun get(groupId: String): Flow<Group?> {
        return groupFlows[groupId] ?: run {
            val group = groupsDataSource.get(groupId)
            groupFlows[groupId] = group
            return group
        }
    }
    suspend fun remove(group: Group) = groupsDataSource.remove(group)
    suspend fun add(group: Group) = groupsDataSource.add(group)
    suspend fun removeUser(groupId: String, userId: String) = groupsDataSource.removeUser(groupId, userId)
}