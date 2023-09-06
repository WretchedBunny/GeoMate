package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.geomate.model.Group
import com.example.geomate.model.User
import com.example.geomate.ui.screens.groups.GroupWithUris
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing

@Composable
fun GroupRow(
    groupWithUris: GroupWithUris,
    onSelect: (Group) -> Unit,
    onRemove: (Group) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect(groupWithUris.group) }
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
    ) {
        ProfilePicturesRow(uris = groupWithUris.uris)
        Text(
            text = groupWithUris.group.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.medium)
                .weight(1f)
        )
        IconButton(
            onClick = { onRemove(groupWithUris.group) },
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
private fun GroupRowPreview() {
    GeoMateTheme {
        GroupRow(
            groupWithUris = GroupWithUris(
                group = Group(
                    users = listOf(
                        User(uid = "3fOeWU9LHnTCNf2TxbbCGnbik0F2"),
                        User(uid = "onYpZ3NG6cSraGnZCSCC4j0JRg73"),
                        User(uid = "tiBC6jmerjbXyv4JdNADqL8BZso1"),
                    ),
                )
            ),
            onSelect = { },
            onRemove = { }
        )
    }
}