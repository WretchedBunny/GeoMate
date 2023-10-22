package com.example.geomate.ui.screens.authentication.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.geomate.R
import com.example.geomate.ui.components.GeoMateIconButton
import com.example.geomate.ui.theme.spacing

@Composable
fun SocialNetworks(
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
