package com.example.geomate.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.geomate.R
import com.example.geomate.ui.theme.GeoMateTheme

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
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview(name = "Facebook icon button")
@Composable
private fun FacebookIconButtonPreview() {
    GeoMateTheme {
        GeoMateIconButton(
            iconId = R.drawable.facebook,
            onClick = { }
        )
    }
}

@Preview(name = "Google icon button")
@Composable
private fun GoogleIconButtonPreview() {
    GeoMateTheme {
        GeoMateIconButton(
            iconId = R.drawable.google,
            onClick = { }
        )
    }
}

@Preview(name = "Twitter icon button")
@Composable
private fun TwitterIconButtonPreview() {
    GeoMateTheme {
        GeoMateIconButton(
            iconId = R.drawable.twitter,
            onClick = { }
        )
    }
}