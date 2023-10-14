package com.example.geomate.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.geomate.R
import com.example.geomate.data.models.User
import com.example.geomate.ui.theme.spacing
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProfileInfo(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        ProfileInfoRow(
            labelId = R.string.profile_title,
            text = "@${user.username}",
        )
        Divider(color = MaterialTheme.colorScheme.secondary)

        if (user.bio.isNotBlank()) {
            ProfileInfoRow(
                labelId = R.string.profile_bio,
                text = user.bio,
            )
            Divider(color = MaterialTheme.colorScheme.secondary)
        }

        ProfileInfoRow(
            labelId = R.string.profile_joined,
            text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(user.joined),
        )
    }
}

@Composable
private fun ProfileInfoRow(
    @StringRes labelId: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            vertical = 12.dp,
            horizontal = MaterialTheme.spacing.extraLarge
        )
    ) {
        Text(
            text = stringResource(id = labelId),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}