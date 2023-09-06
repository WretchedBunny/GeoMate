package com.example.geomate.ui.screens.groups

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GroupsViewModelMock : GroupsViewModel {
    override val uiState: StateFlow<GroupsUiState> = MutableStateFlow(GroupsUiState()).asStateFlow()
    override fun updateSearchQuery(searchQuery: String) { }
    override suspend fun fetchProfilePictures(groupsWithUris: List<GroupWithUris>) {}
}