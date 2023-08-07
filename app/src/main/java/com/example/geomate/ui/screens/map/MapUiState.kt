package com.example.geomate.ui.screens.map

import com.example.geomate.model.Group
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

// TODO: Add markers' position etc...

data class MapUiState(
    val searchQuery: String = "",
    val groups: List<Group> = listOf(),
    val cameraPosition: CameraPositionState = CameraPositionState(
        CameraPosition(
            LatLng(40.772499265817345, -73.97661507692591), 16.0f, 0.0f, 0.0f
        )
    ),
)

val MapUiState.isAllSelected: Boolean get() = groups.all { it.isSelected }
