package com.example.geomate.service.storage

import com.example.geomate.model.Group
import com.example.geomate.model.User

interface StorageService {
    suspend fun addUser(user: User)
    suspend fun getUser(uid: String): User?
    suspend fun addGroup(group: Group)
    suspend fun getGroups(): List<Group>
}