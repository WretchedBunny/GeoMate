package com.example.geomate.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PermContactCalendar
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geomate.R
import com.example.geomate.ui.components.ButtonType
import com.example.geomate.ui.components.Footer
import com.example.geomate.ui.components.GeoMateButton
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.Header
import com.example.geomate.ui.components.LeadingIcon
import com.example.geomate.ui.components.TrailingIcon
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Header(
            title = stringResource(id = R.string.sign_up_title),
            subtitle = stringResource(id = R.string.sign_up_subtitle),
            modifier = Modifier.padding(top = 42.dp, start = 30.dp, end = 30.dp)
        )

        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        HorizontalPager(
            state = pagerState,
            pageCount = 3,
            userScrollEnabled = false,
        ) {
            when (it) {
                0 -> EmailAndPasswordStage(
                    next = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
                1 -> PublicInformationStage(
                    next = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    },
                    prev = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
                2 -> OptionalInformationStage(
                    next = { /* TODO: Create user account and navigate to map */ },
                    prev = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }
        }

        Footer(
            text = stringResource(id = R.string.sign_up_footer),
            clickableText = stringResource(id = R.string.button_sign_up),
            onClick = { /* TODO: Navigate to sign in */ },
            modifier = Modifier.padding(bottom = 42.dp, start = 30.dp, end = 30.dp)
        )
    }
}

@Composable
private fun EmailAndPasswordStage(
    next: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        var isPasswordVisible by remember { mutableStateOf(false) }
        val (passwordTrailingIcon, passwordVisualTransformation) = when (isPasswordVisible) {
            true -> Pair(Icons.Outlined.VisibilityOff, VisualTransformation.None)
            false -> Pair(Icons.Outlined.Visibility, PasswordVisualTransformation())
        }

        GeoMateTextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            leadingIcon = LeadingIcon(Icons.Outlined.Email),
            placeholder = stringResource(id = R.string.email_placeholder)
        )
        GeoMateTextField(
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            leadingIcon = LeadingIcon(Icons.Outlined.Lock),
            trailingIcon = TrailingIcon(
                icon = passwordTrailingIcon,
                onClick = { isPasswordVisible = !isPasswordVisible }
            ),
            placeholder = stringResource(id = R.string.password_placeholder),
            visualTransformation = passwordVisualTransformation
        )
        GeoMateButton(
            text = stringResource(id = R.string.button_continue),
            onClick = next,
            type = ButtonType.Primary
        )
    }
}

@Composable
private fun PublicInformationStage(
    next: () -> Unit,
    prev: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
    ) {
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }

        GeoMateTextField(
            value = firstName,
            onValueChange = { newFirstName -> firstName = newFirstName },
            leadingIcon = LeadingIcon(Icons.Outlined.Person),
            placeholder = stringResource(id = R.string.first_name_placeholder)
        )
        GeoMateTextField(
            value = lastName,
            onValueChange = { newLastName -> lastName = newLastName },
            leadingIcon = LeadingIcon(Icons.Outlined.Person),
            placeholder = stringResource(id = R.string.last_name_placeholder)
        )
        GeoMateTextField(
            value = username,
            onValueChange = { newUsername -> username = newUsername },
            leadingIcon = LeadingIcon(Icons.Outlined.Person),
            placeholder = stringResource(id = R.string.username_placeholder)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
            GeoMateButton(
                text = stringResource(id = R.string.button_back),
                onClick = prev,
                type = ButtonType.Secondary
            )
            GeoMateButton(
                text = stringResource(id = R.string.button_continue),
                onClick = next,
                type = ButtonType.Primary
            )
        }
    }
}

@Composable
private fun OptionalInformationStage(
    next: () -> Unit,
    prev: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
    ) {
        var description by remember { mutableStateOf("") }

        GeoMateTextField(
            value = description,
            onValueChange = { newDescription -> description = newDescription },
            leadingIcon = LeadingIcon(Icons.Outlined.PermContactCalendar),
            placeholder = stringResource(id = R.string.description_placeholder)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
            GeoMateButton(
                text = stringResource(id = R.string.button_back),
                onClick = prev,
                type = ButtonType.Secondary
            )
            GeoMateButton(
                text = stringResource(id = R.string.button_sign_up),
                onClick = next,
                type = ButtonType.Primary
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignUpScreenPreview() {
    GeoMateTheme {
        SignUpScreen()
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
private fun EmailAndPasswordStagePreview() {
    GeoMateTheme {
        EmailAndPasswordStage(
            next = { }
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
private fun PublicInformationStagePreview() {
    GeoMateTheme {
        PublicInformationStage(
            next = { },
            prev = { }
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
private fun OptionalInformationStagePreview() {
    GeoMateTheme {
        OptionalInformationStage(
            next = { },
            prev = { }
        )
    }
}