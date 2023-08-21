package com.example.geomate.ui.screens.map

import com.example.geomate.model.Group
import com.example.geomate.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModelMock : MapViewModel {
    override val uiState = MutableStateFlow(MapUiState()).asStateFlow()
    override fun updateSearchQuery(searchQuery: String) { TODO() }
    override fun toggleGroup(group: Group) { TODO() }
    override fun toggleAllGroups(current: Boolean) { TODO() }
    override fun pointCameraOnUser() { TODO() }
    override suspend fun getUser(uid: String): User? { TODO() }
}