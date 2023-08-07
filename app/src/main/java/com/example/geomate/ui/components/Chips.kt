package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing

@Composable
fun Chips(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (textColor, backgroundColor) = when (isSelected) {
        true -> Pair(
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primary,
        )

        false -> Pair(
            MaterialTheme.colorScheme.onBackground,
            MaterialTheme.colorScheme.background,
        )
    }
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(
                vertical = MaterialTheme.spacing.small,
                horizontal = MaterialTheme.spacing.medium
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = textColor
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