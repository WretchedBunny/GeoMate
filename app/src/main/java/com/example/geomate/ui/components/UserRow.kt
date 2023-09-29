package com.example.geomate.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.geomate.R
import com.example.geomate.data.models.User
import com.example.geomate.ui.theme.spacing
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.fresco.FrescoImage

@Composable
fun UserRow(
    user: Pair<User, Uri>,
    onSelect: (User) -> Unit,
    onRemove: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    val drawableId =
        if (isSystemInDarkTheme()) R.drawable.profile_picture_placeholder_dark
        else R.drawable.profile_picture_placeholder_light

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect(user.first) }
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
    ) {
        FrescoImage(
            imageUrl = user.second.toString(),
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
        Text(
            text = "${user.first.firstName} ${user.first.lastName}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.medium)
                .weight(1f)
        )
        IconButton(
            onClick = { onRemove(user.first) },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = null
            )
        }
    }
}