package com.example.geomate.data.repositories

import com.example.geomate.data.datasources.GroupsDataSource
import com.example.geomate.data.models.Group

class GroupsRepository(private val groupsDataSource: GroupsDataSource) {
    suspend fun getAll(ownerId: String) = groupsDataSource.getAll(ownerId)
    suspend fun add(group: Group) = groupsDataSource.add(group)
    suspend fun remove(group: Group) = groupsDataSource.remove(group)
}