package com.example.geomate.service.account

interface AccountService {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
}