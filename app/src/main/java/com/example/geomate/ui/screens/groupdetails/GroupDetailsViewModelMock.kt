package com.example.geomate.ui.screens.groupdetails

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GroupDetailsViewModelMock : GroupDetailsViewModel {
    override val uiState: StateFlow<GroupDetailsUiState> = TODO()
    override fun updateSearchQuery(searchQuery: String) { }
}