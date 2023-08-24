package com.example.geomate.service.storage

import com.example.geomate.model.User

class StorageServiceMock : StorageService {
    override suspend fun addUser(user: User) {}
    override suspend fun loggedForFirstTime(uid: String) {
        TODO("Not yet implemented")
    }
}