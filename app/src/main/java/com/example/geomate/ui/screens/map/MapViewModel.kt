package com.example.geomate.ui.screens.map

import com.example.geomate.model.Chip
import com.example.geomate.model.User
import kotlinx.coroutines.flow.StateFlow

interface MapViewModel {
    val uiState: StateFlow<MapUiState>
    fun updateSearchQuery(searchQuery: String)
    suspend fun fetchProfilePictureUri(path: String)
    fun startMonitoringUserLocation()
    fun stopMonitoringUserLocation()
    fun toggleChip(chip: Chip)
    fun toggleAllChips(current: Boolean)
    fun pointCameraOnUser()
    suspend fun getUser(uid: String): User?
}