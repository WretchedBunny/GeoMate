package com.example.geomate.ui.screens.friends

import android.net.Uri
import com.example.geomate.data.models.User

data class FriendsUiState(
    val isLoading: Boolean = true,
    val friends: Map<User, Uri> = emptyMap(),
    val matchedFriends: List<User> = emptyList(),
    val searchQuery: String = "",
)
