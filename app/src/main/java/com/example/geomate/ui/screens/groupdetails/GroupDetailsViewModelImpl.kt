package com.example.geomate.ui.screens.groupdetails

import androidx.lifecycle.ViewModel
import com.example.geomate.model.Group
import com.example.geomate.model.GroupWithUris
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GroupDetailsViewModelImpl(group: Group) : ViewModel(), GroupDetailsViewModel {
    private val _uiState = MutableStateFlow(
        GroupDetailsUiState(groupWithUris = GroupWithUris(group))
    )
    override val uiState: StateFlow<GroupDetailsUiState> = _uiState.asStateFlow()

    override fun updateSearchQuery(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }
}