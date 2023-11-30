package com.example.geomate.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.geomate.R

@Composable
fun ProfilePicturePicker(
    bitmap: Bitmap?,
    openPhotoPicker: () -> Unit,
    clearProfilePicture: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profilePictureId = when (isSystemInDarkTheme()) {
        true -> R.drawable.profile_picture_placeholder_dark
        false -> R.drawable.profile_picture_placeholder_light
    }

    Box(modifier = modifier) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )
        } ?: run {
            Image(
                painter = painterResource(id = profilePictureId),
                contentDescription = null,
                modifier = Modifier.clip(CircleShape)
            )
        }
        FilledIconButton(
            onClick = openPhotoPicker,
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
        if (bitmap != null) {
            FilledIconButton(
                onClick = clearProfilePicture,
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
