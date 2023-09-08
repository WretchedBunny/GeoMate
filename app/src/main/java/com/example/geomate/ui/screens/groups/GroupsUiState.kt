package com.example.geomate.ui.screens.groups

import com.example.geomate.model.GroupWithUris

data class GroupsUiState(
    val searchQuery: String = "",
    val groupsWithUris: List<GroupWithUris> = listOf(),
)
