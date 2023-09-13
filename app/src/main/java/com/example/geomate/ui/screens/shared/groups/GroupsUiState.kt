package com.example.geomate.ui.screens.shared.groups

import com.example.geomate.model.Group

data class GroupsUiState(
    val groups: List<Group> = listOf(),
)