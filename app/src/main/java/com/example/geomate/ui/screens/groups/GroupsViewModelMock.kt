package com.example.geomate.ui.screens.groups

import com.example.geomate.model.Group
import com.example.geomate.model.GroupWithUris
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GroupsViewModelMock : GroupsViewModel {
    override val uiState: StateFlow<GroupsUiState> = MutableStateFlow(GroupsUiState()).asStateFlow()
    override fun updateSearchQuery(searchQuery: String) { }
    override suspend fun fetchProfilePictures(groupsWithUris: List<GroupWithUris>) {}
    override fun removeGroup(group: Group): Boolean = true
}