package com.example.geomate.ui.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

// TODO: Change "Color(0xFF7B5401)" to "MaterialTheme.colorScheme.primary" after PR will be accepted and merged
// TODO: Change "Color.White" to "MaterialTheme.colorScheme.onPrimary" after PR will be accepted and merged
// TODO: Change "Color(0xFFF4E8DA)" to "MaterialTheme.colorScheme.secondary" after PR will be accepted and merged
// TODO: Change "Color(0xFF464845)" to "MaterialTheme.colorScheme.onSecondary" after PR will be accepted and merged

enum class ButtonType {
    Primary, Secondary
}

@Composable
fun GeoMateButton(
    text: String,
    onClick: () -> Unit,
    type: ButtonType,
    modifier: Modifier = Modifier
) {
    val (containerColor, contentColor) = when(type) {
        ButtonType.Primary -> Pair(Color(0xFF7B5401), Color.White)
        ButtonType.Secondary -> Pair(Color(0xFFF4E8DA), Color(0xFF464845))
    }

    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = null,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Preview(name = "Primary button")
@Composable
private fun PrimaryButtonPreview() {
    GeoMateButton(
        text = "Continue",
        onClick = { },
        type = ButtonType.Primary
    )
}

@Preview(name = "Secondary button")
@Composable
private fun SecondaryButtonPreview() {
    GeoMateButton(
        text = "Back",
        onClick = { },
        type = ButtonType.Secondary
    )
}