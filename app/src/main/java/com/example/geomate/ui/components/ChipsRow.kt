package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.geomate.R
import com.example.geomate.model.Group
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing

@Composable
fun ChipsRow(
    groups: List<Group>,
    isAllSelected: Boolean,
    toggleGroup: (Group) -> Unit,
    toggleAllGroups: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = modifier
    ) {
        item { Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium - MaterialTheme.spacing.small)) }
        item {
            Chips(
                text = stringResource(id = R.string.map_all),
                isSelected = isAllSelected,
                onClick = { toggleAllGroups(isAllSelected) }
            )
        }
        items(groups) {
            Chips(
                text = it.name,
                isSelected = it.isSelected,
                onClick = { toggleGroup(it) }
            )
        }
        item { Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium - MaterialTheme.spacing.small)) }
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
private fun ChipsRowPreview() {
    GeoMateTheme {
        ChipsRow(
            groups = listOf(
                Group(name = "University", isSelected = true),
                Group(name = "Family", isSelected = true),
                Group(name = "Football team", isSelected = false),
                Group(name = "Discord", isSelected = false),
                Group(name = "Work", isSelected = false),
                Group(name = "Poker club", isSelected = false),
            ),
            isAllSelected = false,
            toggleGroup = { },
            toggleAllGroups = { },
        )
    }
}