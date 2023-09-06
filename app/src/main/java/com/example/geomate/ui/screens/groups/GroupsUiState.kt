package com.example.geomate.ui.screens.groups

import android.net.Uri
import com.example.geomate.model.Group

data class GroupWithUris(
    val group: Group,
    var uris: List<Uri?> = List(group.users.size) { Uri.EMPTY },
)

data class GroupsUiState(
    val searchQuery: String = "",
    val groupsWithUris: List<GroupWithUris> = listOf(),
)
