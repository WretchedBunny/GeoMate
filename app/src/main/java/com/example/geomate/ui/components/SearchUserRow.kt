package com.example.geomate.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.geomate.data.models.User
import com.example.geomate.ui.theme.spacing
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.fresco.FrescoImage

@Composable
fun SearchUserRow(
    user: Pair<User, Uri>,
    onSelect: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onSelect(user.first) }
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
    ) {
        val drawableId =
            if (isSystemInDarkTheme()) R.drawable.profile_picture_placeholder_dark
            else R.drawable.profile_picture_placeholder_light

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
                .size(60.dp)
                .clip(CircleShape)
        )
        
        Column {
            Text(
                text = "${user.first.firstName} ${user.first.lastName}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "@${user.first.username}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}