package com.example.geomate.data.repositories

import android.net.Uri
import com.example.geomate.data.datasources.UsersDataSource
import com.example.geomate.data.models.User
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val usersDataSource: UsersDataSource) {
    suspend fun get(userId: String): Flow<User?> = usersDataSource.get(userId)
    suspend fun getAll(usersIds: List<String>): Flow<List<User>> = usersDataSource.getAll(usersIds)
    suspend fun matchFirstName(searchQuery: String): List<User> = usersDataSource.matchFirstName(searchQuery)
    suspend fun matchLastName(searchQuery: String): List<User> = usersDataSource.matchLastName(searchQuery)
    suspend fun matchUsername(searchQuery: String): List<User> = usersDataSource.matchUsername(searchQuery)
    suspend fun add(user: User) = usersDataSource.add(user)
    suspend fun remove(user: User) = usersDataSource.remove(user)
    suspend fun getProfilePicture(userId: String): Uri = usersDataSource.getProfilePicture(userId)
    suspend fun addProfilePicture(userId: String, uri: Uri) = usersDataSource.addProfilePicture(userId, uri)
    suspend fun sendRecoveryEmail(email: String) = usersDataSource.sendRecoveryEmail(email)
}