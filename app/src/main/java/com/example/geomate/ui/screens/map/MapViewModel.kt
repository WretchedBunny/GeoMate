package com.example.geomate.ui.screens.map

import com.example.geomate.model.Group
import com.example.geomate.model.User
import kotlinx.coroutines.flow.StateFlow

interface MapViewModel {
    val uiState: StateFlow<MapUiState>
    fun updateSearchQuery(searchQuery: String)
    suspend fun fetchProfilePictureUri(path: String)
    fun startMonitoringUserLocation()
    fun stopMonitoringUserLocation()
    fun toggleGroup(group: Group)
    fun toggleAllGroups(current: Boolean)
    fun pointCameraOnUser()
    suspend fun getUser(uid: String): User?
}