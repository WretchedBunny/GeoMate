package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geomate.R
import com.example.geomate.ui.theme.GeoMateTheme

@Composable
fun ProfilePicturePicker(modifier: Modifier = Modifier) {
    val profilePictureId = when (isSystemInDarkTheme()) {
        true -> R.drawable.profile_picture_placeholder_dark
        false -> R.drawable.profile_picture_placeholder_light
    }
    var isProfilePictureSelected by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = profilePictureId),
            contentDescription = null,
            modifier = Modifier.clip(CircleShape)
        )
        FilledIconButton(
            onClick = { /* TODO: Open photo picker */ },
            colors = IconButtonDefaults.filledIconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .border(4.dp, MaterialTheme.colorScheme.background, CircleShape)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Filled.CameraAlt,
                contentDescription = null
            )
        }
        if (isProfilePictureSelected) {
            FilledIconButton(
                onClick = { /* TODO: Clear the photo */ },
                colors = IconButtonDefaults.filledIconButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .border(4.dp, MaterialTheme.colorScheme.background, CircleShape)
                    .align(Alignment.BottomStart)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFF7F0
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF2A2A2A
)
@Composable
private fun ProfilePicturePickerPreview() {
    GeoMateTheme {
        ProfilePicturePicker()
    }
}