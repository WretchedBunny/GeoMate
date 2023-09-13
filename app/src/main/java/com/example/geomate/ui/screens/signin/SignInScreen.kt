package com.example.geomate.ui.screens.signin

import android.app.Activity
import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import com.example.geomate.service.account.EmailPasswordAuthentication
import com.example.geomate.service.account.GoogleAuthentication
import com.example.geomate.service.account.TwitterAuthentication
import com.example.geomate.ui.components.ButtonType
import com.example.geomate.ui.components.Footer
import com.example.geomate.ui.components.GeoMateButton
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.Header
import com.example.geomate.ui.components.InputValidator
import com.example.geomate.ui.components.SocialNetworksRow
import com.example.geomate.ui.components.SupportingButton
import com.example.geomate.ui.components.TextFieldIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.screens.forgotpassword.navigateToForgotPassword
import com.example.geomate.ui.screens.map.navigateToMap
import com.example.geomate.ui.screens.signup.navigateToSignUp
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

fun NavGraphBuilder.signIn(
    uiState: SignInUiState,
    viewModel: SignInViewModel,
    navController: NavController
) {
    composable(Destinations.SIGN_IN_ROUTE) {
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
        launchSingleTop = true
    }
}

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    viewModel: SignInViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val oneTapClient = Identity.getSignInClient(context)
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            val signInCredentials = oneTapClient.getSignInCredentialFromIntent(result.data)
            val googleSignInAuth = GoogleAuthentication(
                viewModel.storageService,
                viewModel.bucketService,
                signInCredentials,
            )
            coroutineScope.launch {
                // TODO: Refactor this part (repeating down below)
                val user = viewModel.signIn(googleSignInAuth)
                if (user != null) {
                    navController.navigateToMap()
                } else {
                    Toast(context).apply {
                        setText("Authentication failed!")
                        duration = Toast.LENGTH_SHORT
                    }.show()
                }
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
                    leadingIcons = listOf(
                        TextFieldIcon {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = it
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
                GeoMateTextField(
                    value = uiState.password,
                    onValueChange = viewModel::updatePassword,
                    leadingIcons = listOf(
                        TextFieldIcon {
                            Icon(
                                imageVector = Icons.Outlined.Lock,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = it
                            )
                        }
                    ),
                    trailingIcons = listOf(
                        TextFieldIcon(
                            onClick = { isPasswordVisible = !isPasswordVisible }
                        ) {
                            Icon(
                                imageVector = passwordTrailingIcon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = it
                            )
                        }
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
                        val isEmailValid = uiState.email.isEmailValid()
                        val isPasswordValid = uiState.password.isPasswordValid()
                        viewModel.updateIsEmailValid(isEmailValid)
                        viewModel.updateIsPasswordValid(isPasswordValid)
                        if (isEmailValid && isPasswordValid) {
                            coroutineScope.launch {
                                val user = viewModel.signIn(
                                    EmailPasswordAuthentication(
                                        email = uiState.email,
                                        password = uiState.password,
                                        storageService = viewModel.storageService,
                                        bucketService = viewModel.bucketService
                                    )
                                )
                                if (user != null) {
                                    navController.navigateToMap()
                                } else {
                                    Toast(context).apply {
                                        setText("Authentication failed!")
                                        duration = Toast.LENGTH_SHORT
                                    }.show()
                                }
                            }
                        }
                    },
                    type = ButtonType.Primary
                )
            }
            SocialNetworksRow(
                onFacebookClick = { /* TODO */ },
                onGoogleClick = {
                    coroutineScope.launch {
                        val signInIntentSender =
                            GoogleAuthentication.getSignInIntentSender(oneTapClient)
                        launcher.launch(
                            IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build()
                        )
                    }
                },
                onTwitterClick = {
                    coroutineScope.launch {
                        val user = viewModel.signIn(
                            TwitterAuthentication(
                                context as Activity,
                                viewModel.storageService,
                                viewModel.bucketService
                            )
                        )
                        if (user != null) {
                            navController.navigateToMap()
                        } else {
                            Toast(context).apply {
                                setText("Authentication  failed!")
                                duration = Toast.LENGTH_SHORT
                            }.show()
                        }
                    }
                }
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