package com.example.geomate.service.account

import com.example.geomate.service.storage.StorageService

interface AccountService {
    val storageService: StorageService
    suspend fun sendRecoveryEmail(email: String)
}