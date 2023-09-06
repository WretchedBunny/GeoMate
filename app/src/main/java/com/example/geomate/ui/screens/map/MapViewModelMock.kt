package com.example.geomate.ui.screens.map

import com.example.geomate.model.Chip
import com.example.geomate.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModelMock : MapViewModel {
    override val uiState = MutableStateFlow(MapUiState()).asStateFlow()
    override fun updateSearchQuery(searchQuery: String) { TODO() }
    override suspend fun fetchProfilePictureUri(path: String) { TODO() }
    override fun startMonitoringUserLocation() { TODO() }
    override fun stopMonitoringUserLocation() { TODO() }
    override fun toggleChip(chip: Chip) { TODO() }
    override fun toggleAllChips(current: Boolean) { TODO() }
    override fun pointCameraOnUser() { TODO() }
    override suspend fun getUser(uid: String): User? { TODO() }
}