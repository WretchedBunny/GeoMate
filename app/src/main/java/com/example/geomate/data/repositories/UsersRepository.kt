package com.example.geomate.data.repositories

import android.net.Uri
import com.example.geomate.data.datasources.UsersDataSource
import com.example.geomate.data.models.User
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val usersDataSource: UsersDataSource) {
    suspend fun get(userId: String): Flow<User?> = usersDataSource.get(userId)
    suspend fun add(user: User) = usersDataSource.add(user)
    suspend fun remove(user: User) = usersDataSource.remove(user)
    suspend fun getProfilePicture(userId: String): Flow<Uri> = usersDataSource.getProfilePicture(userId)
    suspend fun addProfilePicture(userId: String, uri: Uri) = usersDataSource.addProfilePicture(userId, uri)
    suspend fun sendRecoveryEmail(email: String) = usersDataSource.sendRecoveryEmail(email)
}