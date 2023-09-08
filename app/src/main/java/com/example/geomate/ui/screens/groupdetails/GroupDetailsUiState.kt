package com.example.geomate.ui.screens.groupdetails

import com.example.geomate.model.GroupWithUris

data class GroupDetailsUiState(
    val searchQuery: String = "",
    val groupWithUris: GroupWithUris
)
