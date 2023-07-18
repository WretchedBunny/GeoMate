package com.example.geomate.ui.screens.signin

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.geomate.R
import com.example.geomate.ui.components.ButtonType
import com.example.geomate.ui.components.Footer
import com.example.geomate.ui.components.GeoMateButton
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.Header
import com.example.geomate.ui.components.LeadingIcon
import com.example.geomate.ui.components.SupportingButton
import com.example.geomate.ui.components.TrailingIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.screens.forgotpassword.navigateToForgotPassword
import com.example.geomate.ui.screens.signup.navigateToSignUp
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing

fun NavGraphBuilder.signIn(navController: NavController) {
    composable(Destinations.SIGN_IN_ROUTE) {
        SignInScreen(
            navigateToSignUp = navController::navigateToSignUp,
            navigateToForgotPassword = navController::navigateToForgotPassword
        )
    }
}

fun NavController.navigateToSignIn() {
    navigate(Destinations.SIGN_IN_ROUTE) {
        launchSingleTop = false
    }
}

@Composable
fun SignInScreen(
    navigateToSignUp: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    modifier: Modifier = Modifier
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
            title = stringResource(id = R.string.sign_in_title),
            subtitle = stringResource(id = R.string.sign_in_subtitle)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
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
                supportingButton = SupportingButton(
                    text = "Forgot password?",
                    onClick = navigateToForgotPassword
                ),
                visualTransformation = passwordVisualTransformation
            )
            GeoMateButton(
                text = stringResource(id = R.string.button_sign_in),
                onClick = { /* TODO: Authenticate user */ },
                type = ButtonType.Primary
            )
        }

        Footer(
            text = stringResource(id = R.string.sign_in_footer),
            clickableText = stringResource(id = R.string.button_sign_up),
            onClick = navigateToSignUp
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignInScreenPreview() {
    GeoMateTheme {
        SignInScreen(
            navigateToSignUp = { },
            navigateToForgotPassword = { }
        )
    }
}