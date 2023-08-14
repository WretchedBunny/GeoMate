package com.example.geomate.ui.screens.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.geomate.model.Group
import com.example.geomate.model.User
import com.example.geomate.service.storage.StorageService
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MapViewModelImpl(
    application: Application,
    private val storageService: StorageService,
    private val fusedLocationClient: FusedLocationProviderClient,
) : AndroidViewModel(application), MapViewModel {
    private val _uiState = MutableStateFlow(
        MapUiState(
            groups = listOf(
                Group(name = "University", isSelected = false),
                Group(name = "Family", isSelected = false),
                Group(name = "Football team", isSelected = false),
                Group(name = "Discord", isSelected = false),
                Group(name = "Work", isSelected = false),
                Group(name = "Poker club", isSelected = false),
            )
        )
    )
    override val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    override fun updateSearchQuery(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }

    override fun toggleGroup(group: Group) {
        val updatedGroup = group.copy(
            isSelected = !group.isSelected
        )
        val updatedGroups = uiState.value.groups.toMutableList()
        updatedGroups[updatedGroups.indexOf(group)] = updatedGroup
        _uiState.update {
            it.copy(groups = updatedGroups)
        }
    }

    override fun toggleAllGroups(current: Boolean) {
        val updatedGroups = uiState.value.groups.map {
            it.copy(isSelected = !current)
        }
        _uiState.update {
            it.copy(groups = updatedGroups)
        }
    }

    override fun pointCameraOnUser() {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(uid: String): User? {
        return storageService.getUser(uid)
    }
}