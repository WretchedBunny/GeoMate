package com.example.geomate.ui.screens.groupdetails

import android.net.Uri
import com.example.geomate.data.models.User


data class GroupDetailsUiState(
    val groupId: String = "",
    val name: String = "",
    val users: Map<User, Uri> = emptyMap(),
    val isLoading: Boolean = true,
)
