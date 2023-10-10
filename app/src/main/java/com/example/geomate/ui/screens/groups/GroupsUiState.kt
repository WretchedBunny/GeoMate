package com.example.geomate.ui.screens.groups

import android.net.Uri
import com.example.geomate.data.models.Group

data class GroupsUiState(
    val searchQuery: String = "",
    val groups: MutableMap<Group, List<Uri>> = mutableMapOf(
        Group(
            "efef",
            "fefefe",
            "23efw"
        ) to listOf<Uri>()
    ),
)
