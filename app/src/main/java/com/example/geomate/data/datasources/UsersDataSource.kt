package com.example.geomate.data.datasources

import android.net.Uri
import com.example.geomate.data.models.User
import kotlinx.coroutines.flow.Flow

interface UsersDataSource {
    suspend fun get(userId: String): Flow<User?>
    suspend fun add(user: User)
    suspend fun remove(user: User)
    suspend fun getProfilePicture(userId: String): Flow<Uri>
    suspend fun addProfilePicture(userId: String, uri: Uri)
    suspend fun sendRecoveryEmail(email: String)
}