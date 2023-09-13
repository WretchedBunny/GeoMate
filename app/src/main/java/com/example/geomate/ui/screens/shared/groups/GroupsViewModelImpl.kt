package com.example.geomate.ui.screens.shared.groups

import androidx.lifecycle.ViewModel
import com.example.geomate.model.Group
import com.example.geomate.service.storage.StorageService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GroupsViewModelImpl(
    private val storageService: StorageService
) : ViewModel(), GroupsViewModel {
    private val _uiState = MutableStateFlow(GroupsUiState())
    override val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    override suspend fun fetchGroups() {
        _uiState.update { it.copy(groups = storageService.getGroups()) }
    }

    override suspend fun addGroup(group: Group) {
        storageService.addGroup(group)
    }

    override suspend fun removeGroup(group: Group) {
        TODO("Not yet implemented")
    }
}