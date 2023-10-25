package com.example.geomate.ui.screens.profile

import android.net.Uri
import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.User

data class ProfileUiState(
    val profilePictureUri: Uri? = null,
    val user: User = User(),
    val friendshipRequest: FriendshipRequest? = FriendshipRequest(),
    val isMenuVisible: Boolean = false,
    val isLoading: Boolean = true,
)
