package com.example.geomate.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.geomate.R

// TODO: Change "Color(0xFF464845)" to "MaterialTheme.colorScheme.onBackground" after PR will be accepted and merged

@Composable
fun GeoMateIconButton(
    @DrawableRes iconId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = Color(0xFF464845)
        )
    }
}

@Preview(name = "Facebook icon button")
@Composable
private fun FacebookIconButtonPreview() {
    GeoMateIconButton(
        iconId = R.drawable.facebook,
        onClick = { }
    )
}

@Preview(name = "Google icon button")
@Composable
private fun GoogleIconButtonPreview() {
    GeoMateIconButton(
        iconId = R.drawable.google,
        onClick = { }
    )
}

@Preview(name = "Twitter icon button")
@Composable
private fun TwitterIconButtonPreview() {
    GeoMateIconButton(
        iconId = R.drawable.twitter,
        onClick = { }
    )
}