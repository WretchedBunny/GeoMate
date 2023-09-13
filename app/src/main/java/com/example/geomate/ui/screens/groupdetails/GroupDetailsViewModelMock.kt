package com.example.geomate.ui.screens.groupdetails

import kotlinx.coroutines.flow.StateFlow

class GroupDetailsViewModelMock : GroupDetailsViewModel {
    override val uiState: StateFlow<GroupDetailsUiState> = TODO()
    override fun updateSearchQuery(searchQuery: String) { }
}