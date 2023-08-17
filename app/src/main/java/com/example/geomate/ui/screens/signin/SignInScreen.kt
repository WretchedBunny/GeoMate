package com.example.geomate.ui.screens.signin

import android.content.res.Configuration
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.geomate.R
import com.example.geomate.ext.isEmailValid
import com.example.geomate.ext.isPasswordValid
import com.example.geomate.ui.components.ButtonType
import com.example.geomate.ui.components.Footer
import com.example.geomate.ui.components.GeoMateButton
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.Header
import com.example.geomate.ui.components.InputValidator
import com.example.geomate.ui.components.LeadingIcon
import com.example.geomate.ui.components.SocialNetworksRow
import com.example.geomate.ui.components.SupportingButton
import com.example.geomate.ui.components.TrailingIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.screens.forgotpassword.navigateToForgotPassword
import com.example.geomate.ui.screens.signup.navigateToSignUp
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing
import kotlinx.coroutines.launch


fun NavGraphBuilder.signIn(
    navController: NavController,
    viewModel: SignInViewModel,
) {
    composable(Destinations.SIGN_IN_ROUTE) {
        val uiState by viewModel.uiState.collectAsState()
        SignInScreen(
            uiState = uiState,
            viewModel = viewModel,
            navController = navController
        )
    }
}

fun NavController.navigateToSignIn() {
    popBackStack()
    navigate(Destinations.SIGN_IN_ROUTE) {
        launchSingleTop = false
    }
}

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    viewModel: SignInViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            Log.d("SignInScreen", "${result.resultCode}")
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                coroutineScope.launch {
                    val signInResult = viewModel.accountService.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    //viewModel.onSignInResult(signInResult)
                }
            }
        }
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
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            ) {
                var isPasswordVisible by remember { mutableStateOf(false) }
                val (passwordTrailingIcon, passwordVisualTransformation) = when (isPasswordVisible) {
                    true -> Pair(Icons.Outlined.VisibilityOff, VisualTransformation.None)
                    false -> Pair(Icons.Outlined.Visibility, PasswordVisualTransformation())
                }

                GeoMateTextField(
                    value = uiState.email,
                    onValueChange = viewModel::updateEmail,
                    leadingIcon = LeadingIcon(Icons.Outlined.Email),
                    placeholder = stringResource(id = R.string.email_placeholder),
                    inputValidator = InputValidator(
                        isValid = uiState.isEmailValid,
                        updateIsValid = viewModel::updateIsEmailValid,
                        rule = String::isEmailValid,
                        errorMessage = stringResource(id = R.string.invalid_email)
                    )
                )
                GeoMateTextField(
                    value = uiState.password,
                    onValueChange = viewModel::updatePassword,
                    leadingIcon = LeadingIcon(Icons.Outlined.Lock),
                    trailingIcon = TrailingIcon(
                        icon = passwordTrailingIcon,
                        onClick = { isPasswordVisible = !isPasswordVisible }
                    ),
                    placeholder = stringResource(id = R.string.password_placeholder),
                    supportingButton = SupportingButton(
                        text = stringResource(id = R.string.button_forgot_password),
                        onClick = navController::navigateToForgotPassword
                    ),
                    inputValidator = InputValidator(
                        isValid = uiState.isPasswordValid,
                        updateIsValid = viewModel::updateIsPasswordValid,
                        rule = { it.length in 8..255 },
                        errorMessage = stringResource(id = R.string.invalid_password)
                    ),
                    visualTransformation = passwordVisualTransformation
                )
                GeoMateButton(
                    text = stringResource(id = R.string.button_sign_in),
                    onClick = {
                        viewModel.updateIsEmailValid(uiState.email.isEmailValid())
                        viewModel.updateIsPasswordValid(uiState.password.isPasswordValid())
                        if (uiState.isEmailValid && uiState.isPasswordValid) {
                            val result = viewModel.onSignInClick()
                            if (result) {
                                // TODO: Navigate to the map screen
                            } else {
                                // TODO: Display error message
                            }
                        }
                    },
                    type = ButtonType.Primary
                )
            }
            SocialNetworksRow(
                onFacebookClick = viewModel::onFacebookClick,
                onGoogleClick = {
                    coroutineScope.launch {
                        val signInIntentSender = viewModel.accountService.googleSignIn()
                        Log.d("SignInScreen", "${signInIntentSender?.equals(null)}")
                        launcher.launch(
                            IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build()
                        )
                    }
                },
                onTwitterClick = viewModel::onTwitterClick
            )
        }

        Footer(
            text = stringResource(id = R.string.sign_in_footer),
            clickableText = stringResource(id = R.string.button_sign_up),
            onClick = navController::navigateToSignUp
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignInScreenPreview() {
    GeoMateTheme {
        SignInScreen(
            uiState = SignInUiState(),
            viewModel = SignInViewModelMock(),
            navController = NavController(LocalContext.current)
        )
    }
}