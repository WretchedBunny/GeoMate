package com.example.geomate.ui.screens.groupdetails

import kotlinx.coroutines.flow.StateFlow

interface GroupDetailsViewModel {
    val uiState: StateFlow<GroupDetailsUiState>
    fun updateSearchQuery(searchQuery: String)
}