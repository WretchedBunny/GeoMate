package com.example.geomate.ui.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.geomate.ui.theme.GeoMateTheme

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
        ButtonType.Primary -> Pair(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
        ButtonType.Secondary -> Pair(MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.onSecondary)
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
    GeoMateTheme {
        GeoMateButton(
            text = "Continue",
            onClick = { },
            type = ButtonType.Primary
        )
    }
}

@Preview(name = "Secondary button")
@Composable
private fun SecondaryButtonPreview() {
    GeoMateTheme {
        GeoMateButton(
            text = "Back",
            onClick = { },
            type = ButtonType.Secondary
        )
    }
}