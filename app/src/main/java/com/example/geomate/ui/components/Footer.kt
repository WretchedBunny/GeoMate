package com.example.geomate.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// TODO: Change "16.dp" to "MaterialTheme.spacing.medium" after PR will be accepted and merged
// TODO: Change "Color(0xFF7B5401)" to "MaterialTheme.colorScheme.primary" after PR will be accepted and merged
// TODO: Change "Color(0xFF464845)" to "MaterialTheme.colorScheme.onBackground" after PR will be accepted and merged

@Composable
fun Footer(
    text: String,
    clickableText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF464845)
        )
        Text(
            text = clickableText,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF7B5401),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Preview
@Composable
private fun FooterPreview() {
    Footer(
        text = "Don't have an account?",
        clickableText = "Sign up",
        onClick = { }
    )
}