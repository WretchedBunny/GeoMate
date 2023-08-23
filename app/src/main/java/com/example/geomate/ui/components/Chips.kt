package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing

@Composable
fun Chips(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val (contentColor, containerColor) = when (isSelected) {
        true -> Pair(
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primary,
        )
        false -> Pair(
            MaterialTheme.colorScheme.onBackground,
            MaterialTheme.colorScheme.background,
        )
    }
    val padding = if (icon != null) {
        PaddingValues(
            start = MaterialTheme.spacing.small,
            top = MaterialTheme.spacing.small,
            end = MaterialTheme.spacing.medium,
            bottom = MaterialTheme.spacing.small,
        )
    } else {
        PaddingValues(
            horizontal = MaterialTheme.spacing.medium,
            vertical = MaterialTheme.spacing.small,
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(containerColor)
            .clickable { onClick() }
            .padding(padding)
    ) {
        icon?.let {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(19.dp)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = contentColor
        )
    }
}

private class IsSelectedProvider : PreviewParameterProvider<Boolean> {
    override val values = listOf(true, false).asSequence()
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChipsPreview(
    @PreviewParameter(provider = IsSelectedProvider::class) isSelected: Boolean,
) {
    GeoMateTheme {
        Chips(
            text = "University",
            isSelected = isSelected,
            onClick = { }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChipsWithIconPreview(
    @PreviewParameter(provider = IsSelectedProvider::class) isSelected: Boolean,
) {
    GeoMateTheme {
        Chips(
            icon = Icons.Outlined.Add,
            text = "New",
            isSelected = isSelected,
            onClick = { }
        )
    }
}