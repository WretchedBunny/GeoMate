package com.example.geomate.ui.screens.groupdetails

import android.net.Uri
import com.example.geomate.data.models.User


data class GroupDetailsUiState(
    val name: String = "",
    val users: MutableMap<User, Uri> = mutableMapOf()
)
