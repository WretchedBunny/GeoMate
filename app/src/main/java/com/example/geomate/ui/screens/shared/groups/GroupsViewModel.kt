package com.example.geomate.ui.screens.shared.groups

import com.example.geomate.model.Group
import kotlinx.coroutines.flow.StateFlow

interface GroupsViewModel {
    val uiState: StateFlow<GroupsUiState>
    suspend fun fetchGroups()
    suspend fun addGroup(group: Group)
    suspend fun removeGroup(group: Group)
}