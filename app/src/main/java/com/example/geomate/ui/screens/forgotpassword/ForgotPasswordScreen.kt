package com.example.geomate.ui.screens.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.geomate.R
import com.example.geomate.ext.isEmailValid
import com.example.geomate.ui.components.ButtonType
import com.example.geomate.ui.components.Footer
import com.example.geomate.ui.components.GeoMateButton
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.Header
import com.example.geomate.ui.components.InputValidator
import com.example.geomate.ui.components.TextFieldIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.screens.signin.navigateToSignIn
import com.example.geomate.ui.theme.spacing

fun NavGraphBuilder.forgotPassword(
    viewModel: ForgotPasswordViewModel,
    navController: NavController,
) {
    composable(Destinations.FORGOT_PASSWORD_ROUTE) {
        val uiState by viewModel.uiState.collectAsState()
        ForgotPasswordScreen(
            uiState = uiState,
            viewModel = viewModel,
            navController = navController
        )
    }
}

fun NavController.navigateToForgotPassword() {
    popBackStack()
    navigate(Destinations.FORGOT_PASSWORD_ROUTE) {
        launchSingleTop = true
    }
}

@Composable
fun ForgotPasswordScreen(
    uiState: ForgotPasswordUiState,
    viewModel: ForgotPasswordViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 42.dp, horizontal = 30.dp)
    ) {
        Header(
            title = stringResource(id = R.string.forgot_password_title),
            subtitle = stringResource(id = R.string.forgot_password_subtitle)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        ) {
            GeoMateTextField(
                value = uiState.email,
                onValueChange = viewModel::updateEmail,
                leadingIcons = listOf(
                    TextFieldIcon {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = it,
                        )
                    }
                ),
                placeholder = stringResource(id = R.string.email_placeholder),
                inputValidator = InputValidator(
                    isValid = uiState.isEmailValid,
                    updateIsValid = viewModel::updateIsEmailValid,
                    rule = String::isEmailValid,
                    errorMessage = stringResource(id = R.string.invalid_email)
                )
            )
            GeoMateButton(
                text = stringResource(id = R.string.button_reset_password),
                onClick = {
                    viewModel.updateIsEmailValid(uiState.email.isEmailValid())
                    if (uiState.isEmailValid) {
                        viewModel.onResetClick()
                    }
                },
                type = ButtonType.Primary
            )
        }

        Footer(
            text = stringResource(id = R.string.forgot_password_footer),
            clickableText = stringResource(id = R.string.button_back),
            onClick = navController::navigateToSignIn
        )
    }
}