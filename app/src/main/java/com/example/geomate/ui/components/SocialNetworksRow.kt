package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.geomate.R
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing

@Composable
fun SocialNetworksRow(
    onFacebookClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onTwitterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.or_continue_with),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)) {
            GeoMateIconButton(
                iconId = R.drawable.facebook,
                onClick = onFacebookClick
            )
            GeoMateIconButton(
                iconId = R.drawable.google,
                onClick = onGoogleClick
            )
            GeoMateIconButton(
                iconId = R.drawable.twitter,
                onClick = onTwitterClick
            )
        }
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
private fun SocialNetworksRowPreview() {
    GeoMateTheme {
        SocialNetworksRow(
            onFacebookClick = { },
            onGoogleClick = { },
            onTwitterClick = { }
        )
    }
}