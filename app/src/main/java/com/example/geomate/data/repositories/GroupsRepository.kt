package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.GroupsDataSource
import com.example.geomate.data.models.Group
import kotlinx.coroutines.flow.Flow

class GroupsRepository(private val groupsDataSource: GroupsDataSource) {
    private val allGroupsFlows: MutableMap<String, Flow<List<Group>>> = mutableMapOf()
    private val groupFlows: MutableMap<String, Flow<Group?>> = mutableMapOf()

    suspend fun getAllAsFlow(ownerId: String): Flow<List<Group>> {
        return allGroupsFlows[ownerId] ?: run {
            val groups = groupsDataSource.getAllAsFlow(ownerId)
            allGroupsFlows[ownerId] = groups
            return groups
        }
    }

    suspend fun getSingleAsFlow(groupId: String): Flow<Group?> {
        return groupFlows[groupId] ?: run {
            val group = groupsDataSource.getSingleAsFlow(groupId)
            groupFlows[groupId] = group
            return group
        }
    }

    suspend fun update(groupId: String, name: String, users: List<String>) {
        groupsDataSource.update(groupId, name, users)
    }

    suspend fun remove(group: Group) = groupsDataSource.remove(group)

    suspend fun add(group: Group) = groupsDataSource.add(group)
}