package com.example.geomate.ui.screens.map

import com.example.geomate.model.Group
import kotlinx.coroutines.flow.StateFlow

interface MapViewModel {
    val uiState: StateFlow<MapUiState>
    fun updateSearchQuery(searchQuery: String)
    fun toggleGroup(group: Group)
    fun toggleAllGroups(current: Boolean)
    fun pointCameraOnUser()
}