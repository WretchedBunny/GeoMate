package com.example.geomate.service.account

import com.example.geomate.service.storage.FirebaseStorageService
import com.example.geomate.service.storage.StorageService
import com.google.firebase.firestore.FirebaseFirestore

class AccountServiceMock : AccountService {
    override val storageService: StorageService =
        FirebaseStorageService(FirebaseFirestore.getInstance())

    override suspend fun sendRecoveryEmail(email: String) {}
}