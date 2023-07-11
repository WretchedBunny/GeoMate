package com.example.geomate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// TODO: Change "8.dp" to "MaterialTheme.spacing.small" after PR will be accepted and merged
// TODO: Change "Color(0xFF464845)" to "MaterialTheme.colorScheme.onBackground" after PR will be accepted and merged

@Composable
fun Header(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFF464845)
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color(0xFF464845)
        )
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    Header(
        title = "Welcome back",
        subtitle = "We happy to see you again. To use your account, you should log in in first."
    )
}