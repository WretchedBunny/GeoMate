package com.example.geomate.ui.screens.signin

import com.example.geomate.service.account.Authentication
import com.example.geomate.service.bucket.BucketService
import com.example.geomate.service.bucket.BucketServiceMock
import com.example.geomate.service.storage.StorageService
import com.example.geomate.service.storage.StorageServiceMock
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModelMock : SignInViewModel {
    override val uiState = MutableStateFlow(SignInUiState()).asStateFlow()
    override val storageService: StorageService = StorageServiceMock()
    override val bucketService: BucketService = BucketServiceMock()

    override fun updateEmail(email: String) {}
    override fun updatePassword(password: String) {}
    override fun updateIsEmailValid(isEmailValid: Boolean) {}
    override fun updateIsPasswordValid(isPasswordValid: Boolean) {}
    override suspend fun signIn(authentication: Authentication): FirebaseUser? = null
}