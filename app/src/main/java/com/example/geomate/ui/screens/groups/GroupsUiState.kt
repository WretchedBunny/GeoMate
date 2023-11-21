package com.example.geomate.ui.screens.groups

import android.net.Uri
import com.example.geomate.data.models.Group

data class GroupsUiState(
    val searchQuery: String = "",
    val groups: Map<Group, List<Uri>> = emptyMap(),
    val matchedGroups: List<Group> = emptyList(),
    val isLoading: Boolean = true,
)
