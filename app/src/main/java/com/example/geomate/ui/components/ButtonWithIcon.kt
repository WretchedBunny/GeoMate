package com.example.geomate.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.geomate.ui.theme.spacing

@Composable
fun GeoMateButtonWithIcon(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    type: ButtonType,
    modifier: Modifier = Modifier
) {
    val (containerColor, contentColor) = when (type) {
        ButtonType.Primary -> Pair(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
        ButtonType.Secondary -> Pair(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.onBackground)
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
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = modifier.width(MaterialTheme.spacing.small))
        Text(text = text)
    }
}