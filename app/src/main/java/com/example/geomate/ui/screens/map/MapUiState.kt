package com.example.geomate.ui.screens.map

import android.net.Uri
import com.example.geomate.model.Chip
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

data class MapUiState(
    val profilePictureUri: Uri? = null,
    val searchQuery: String = "",
    val chips: List<Chip> = listOf(),
    val userMarker: LatLng = LatLng(40.772499265817345, -73.97661507692591),
    val cameraPosition: CameraPositionState = CameraPositionState(
        CameraPosition(
            userMarker, 16.0f, 0.0f, 0.0f
        )
    ),
)

val MapUiState.isAllSelected: Boolean get() = chips.all { it.isSelected }
