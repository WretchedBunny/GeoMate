package com.example.geomate.ui.screens.groups

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

interface GroupsViewModel {
    val uiState: StateFlow<GroupsUiState>
    fun updateSearchQuery(searchQuery: String)
    suspend fun fetchProfilePictures(groupsWithUris: List<GroupWithUris>)
}