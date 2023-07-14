package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    val (containerColor, contentColor) = when (type) {
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
private fun PrimaryButtonPreview() {
    GeoMateTheme {
        GeoMateButton(
            text = "Continue",
            onClick = { },
            type = ButtonType.Primary
        )
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
private fun SecondaryButtonPreview() {
    GeoMateTheme {
        GeoMateButton(
            text = "Back",
            onClick = { },
            type = ButtonType.Secondary
        )
    }
}