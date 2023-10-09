package com.example.geomate.ui.screens.profile

import android.net.Uri
import com.example.geomate.data.models.User

data class ProfileUiState(
    val profilePictureUri: Uri = Uri.EMPTY,
    val user: User = User(),
    val isMenuVisible: Boolean = false,
    val isLoading: Boolean = true,
)
