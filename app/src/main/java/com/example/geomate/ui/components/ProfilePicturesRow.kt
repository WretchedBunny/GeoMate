package com.example.geomate.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.geomate.R
import com.example.geomate.ui.theme.spacing
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.fresco.FrescoImage

@Composable
fun ProfilePicturesRow(
    uris: List<Uri?>,
    modifier: Modifier = Modifier,
) {
    val drawableId =
        if (isSystemInDarkTheme()) R.drawable.profile_picture_placeholder_dark
        else R.drawable.profile_picture_placeholder_light

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(-MaterialTheme.spacing.medium)
    ) {
        uris.take(3).forEach { uri ->
            FrescoImage(
                imageUrl = uri.toString(),
                failure = {
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = null
                    )
                },
                imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                modifier = modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape),
            )
        }
        if (uris.size - 3 > 0) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
                    .size(42.dp)
            ) {
                Text(
                    text = "+${uris.size - 3}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}
