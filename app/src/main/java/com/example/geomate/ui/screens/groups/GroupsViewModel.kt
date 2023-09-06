package com.example.geomate.ui.screens.groups

import com.example.geomate.model.Group
import kotlinx.coroutines.flow.StateFlow

interface GroupsViewModel {
    val uiState: StateFlow<GroupsUiState>
    fun updateSearchQuery(searchQuery: String)
    suspend fun fetchProfilePictures(groupsWithUris: List<GroupWithUris>)
    fun removeGroup(group: Group): Boolean
}