package com.example.geomate.ui.screens.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.geomate.model.Group
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


val _groups = listOf(
    Group(name = "University", isSelected = false),
    Group(name = "Family", isSelected = false),
    Group(name = "Football team", isSelected = false),
    Group(name = "Discord", isSelected = false),
    Group(name = "Work", isSelected = false),
    Group(name = "Poker club", isSelected = false),
)

class MapViewModel(
    application: Application,
    private val fusedLocationClient: FusedLocationProviderClient,
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(MapUiState(groups = _groups))
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val searchQuery
        get() = uiState.value.searchQuery
    private val groups
        get() = uiState.value.groups

    fun toggleAll(current: Boolean) {
        val updatedGroups = groups.map {
            it.copy(isSelected = !current)
        }
        _uiState.update {
            it.copy(groups = updatedGroups)
        }
    }

    fun toggleGroup(group: Group) {
        val updatedGroup = group.copy(
            isSelected = !group.isSelected
        )
        val updatedGroups = groups.toMutableList()
        updatedGroups[updatedGroups.indexOf(group)] = updatedGroup
        _uiState.update {
            it.copy(groups = updatedGroups)
        }
    }

    fun updateSearchQuery(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }
}