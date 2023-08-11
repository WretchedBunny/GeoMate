package com.example.geomate.service.account

class AccountServiceMock : AccountService {
    override suspend fun signIn(email: String, password: String) {}
    override suspend fun signUp(email: String, password: String) {}
    override suspend fun sendRecoveryEmail(email: String) {}
}