package com.example.geomate.ui.screens.groupdetails

import android.net.Uri
import com.example.geomate.data.models.User

data class SelectFriendUiState(
    val isLoading: Boolean = true,
    val visible: Boolean = false,
    val friends: Map<User, Uri> = emptyMap(),
    val matchedFriends: List<User> = emptyList(),
    val selectedFriends: List<User> = emptyList(),
    val searchQuery: String = "",
)
