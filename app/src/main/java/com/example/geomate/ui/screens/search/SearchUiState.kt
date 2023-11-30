package com.example.geomate.ui.screens.search

import android.net.Uri
import com.example.geomate.data.models.User

data class SearchUiState(
    val duringDebounce: Boolean = false,
    val isLoading: Boolean = false,
    val users: Map<User, Uri> = mapOf()
)
