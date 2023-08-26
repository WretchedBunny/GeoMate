package com.example.geomate.service.storage

import com.example.geomate.model.User

interface StorageService {
    suspend fun addUser(user: User)
}