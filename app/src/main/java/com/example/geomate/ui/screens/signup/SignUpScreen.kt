package com.example.geomate.ui.screens.signup

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Icon
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
import com.example.geomate.ext.isFirstNameValid
import com.example.geomate.ext.isLastNameValid
import com.example.geomate.ext.isPasswordValid
import com.example.geomate.ext.isUsernameValid
import com.example.geomate.service.account.EmailPasswordAuthentication
import com.example.geomate.service.account.GoogleAuthentication
import com.example.geomate.ui.components.ButtonType
import com.example.geomate.ui.components.Footer
import com.example.geomate.ui.components.GeoMateButton
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.Header
import com.example.geomate.ui.components.InputValidator
import com.example.geomate.ui.components.LeadingIcon
import com.example.geomate.ui.components.ProfilePicturePicker
import com.example.geomate.ui.components.SocialNetworksRow
import com.example.geomate.ui.components.TrailingIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.screens.map.navigateToMap
import com.example.geomate.ui.screens.signin.navigateToSignIn
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

fun NavGraphBuilder.signUp(
    navController: NavController,
    viewModel: SignUpViewModelImpl,
) {
    composable(Destinations.SIGN_UP_ROUTE) {
        val uiState by viewModel.uiState.collectAsState()
        SignUpScreen(
            uiState = uiState,
            viewModel = viewModel,
            navController = navController
        )
    }
}

fun NavController.navigateToSignUp() {
    popBackStack()
    navigate(Destinations.SIGN_UP_ROUTE) {
        launchSingleTop = false
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    viewModel: SignUpViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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

        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { 3 }
        )
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
        ) {
            when (it) {
                0 -> EmailAndPasswordStage(
                    uiState = uiState,
                    viewModel = viewModel,
                    next = {
                        val isEmailValid = uiState.email.isEmailValid()
                        viewModel.updateIsEmailValid(isEmailValid)

                        val isPasswordValid = uiState.password.isPasswordValid()
                        viewModel.updateIsPasswordValid(isEmailValid)

                        if (isEmailValid && isPasswordValid) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        }
                    },
                    modifier = Modifier.padding(horizontal = 30.dp)
                )

                1 -> PublicInformationStage(
                    uiState = uiState,
                    viewModel = viewModel,
                    next = {
                        viewModel.updateIsFirstNameValid(uiState.firstName.isFirstNameValid())
                        viewModel.updateIsLastNameValid(uiState.lastName.isLastNameValid())
                        viewModel.updateIsUsernameValid(uiState.username.isUsernameValid())

                        if (uiState.isFirstNameValid && uiState.isLastNameValid && uiState.isUsernameValid) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(2)
                            }
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
                    uiState = uiState,
                    viewModel = viewModel,
                    next = {
                        coroutineScope.launch {
                            val user = viewModel.signUp(
                                EmailPasswordAuthentication(
                                    FirebaseAuth.getInstance(),
                                    uiState.email,
                                    uiState.password
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
                    },
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
            clickableText = stringResource(id = R.string.button_sign_in),
            onClick = navController::navigateToSignIn,
            modifier = Modifier.padding(bottom = 42.dp, start = 30.dp, end = 30.dp)
        )
    }
}

@Composable
private fun EmailAndPasswordStage(
    uiState: SignUpUiState,
    viewModel: SignUpViewModel,
    next: () -> Unit,
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
                FirebaseAuth.getInstance(),
                viewModel.storageService,
                signInCredentials,
            )
            coroutineScope.launch {
                viewModel.signUp(googleSignInAuth)
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        modifier = modifier
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
                    LeadingIcon(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = null,
                                modifier = it
                            )
                        }
                    )
                ),
                placeholder = stringResource(id = R.string.email_placeholder),
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
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
                    LeadingIcon(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Lock,
                                contentDescription = null,
                                modifier = it
                            )
                        }
                    )
                ),
                trailingIcons = listOf(
                    TrailingIcon(
                        icon = {
                            Icon(
                                imageVector = passwordTrailingIcon,
                                contentDescription = null,
                                modifier = it
                            )
                        },
                        onClick = { isPasswordVisible = !isPasswordVisible }
                    )
                ),
                placeholder = stringResource(id = R.string.password_placeholder),
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                inputValidator = InputValidator(
                    isValid = uiState.isPasswordValid,
                    updateIsValid = viewModel::updateIsPasswordValid,
                    rule = String::isPasswordValid,
                    errorMessage = stringResource(id = R.string.invalid_password)
                ),
                visualTransformation = passwordVisualTransformation
            )
            GeoMateButton(
                text = stringResource(id = R.string.button_continue),
                onClick = next,
                type = ButtonType.Primary
            )
        }
        SocialNetworksRow(
            onFacebookClick = { /* TODO */ },
            onGoogleClick = {
                coroutineScope.launch {
                    val signInIntentSender = GoogleAuthentication.getSignUpIntentSender(oneTapClient)
                    launcher.launch(
                        IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build()
                    )
                }
            },
            onTwitterClick = { /* TODO */ }
        )
    }
}

@Composable
private fun PublicInformationStage(
    uiState: SignUpUiState,
    viewModel: SignUpViewModel,
    next: () -> Unit,
    prev: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
    ) {
        GeoMateTextField(
            value = uiState.firstName,
            onValueChange = viewModel::updateFirstName,
            leadingIcons = listOf(
                LeadingIcon(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            modifier = it
                        )
                    }
                )
            ),
            placeholder = stringResource(id = R.string.first_name_placeholder),
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            inputValidator = InputValidator(
                isValid = uiState.isFirstNameValid,
                updateIsValid = viewModel::updateIsFirstNameValid,
                rule = { it.length in 1..30 },
                errorMessage = stringResource(id = R.string.invalid_firstname_sign_up)
            )
        )
        GeoMateTextField(
            value = uiState.lastName,
            onValueChange = viewModel::updateLastName,
            leadingIcons = listOf(
                LeadingIcon(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            modifier = it
                        )
                    }
                )
            ),
            placeholder = stringResource(id = R.string.last_name_placeholder),
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            inputValidator = InputValidator(
                isValid = uiState.isLastNameValid,
                updateIsValid = viewModel::updateIsLastNameValid,
                rule = { it.length in 1..30 },
                errorMessage = stringResource(id = R.string.invalid_lastname_sign_up)
            )
        )
        GeoMateTextField(
            value = uiState.username,
            onValueChange = viewModel::updateUsername,
            leadingIcons = listOf(
                LeadingIcon(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            modifier = it
                        )
                    }
                )
            ),
            placeholder = stringResource(id = R.string.username_placeholder),
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            inputValidator = InputValidator(
                isValid = uiState.isUsernameValid,
                updateIsValid = viewModel::updateIsUsernameValid,
                rule = String::isUsernameValid, // TODO: Check if username is already taken
                errorMessage = stringResource(id = R.string.invalid_username_sign_up)
            )
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
    uiState: SignUpUiState,
    viewModel: SignUpViewModel,
    next: () -> Unit,
    prev: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.updateProfilePictureUri(uri)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
    ) {
        val bitmap: Bitmap? = uiState.profilePictureUri?.run {
            val source = ImageDecoder.createSource(context.contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }

        ProfilePicturePicker(
            bitmap = bitmap,
            openPhotoPicker = { launcher.launch("image/*") },
            clearProfilePicture = { viewModel.updateProfilePictureUri(null) }
        )
        GeoMateTextField(
            value = uiState.bio,
            onValueChange = viewModel::updateBio,
            leadingIcons = listOf(
                LeadingIcon(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.PermContactCalendar,
                            contentDescription = null,
                            modifier = it
                        )
                    }
                )
            ),
            placeholder = stringResource(id = R.string.description_placeholder),
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            supportingText = "Optional",
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
        SignUpScreen(
            uiState = SignUpUiState(),
            viewModel = SignUpViewModelMock(),
            navController = NavController(LocalContext.current)
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
private fun EmailAndPasswordStagePreview() {
    GeoMateTheme {
        EmailAndPasswordStage(
            uiState = SignUpUiState(),
            viewModel = SignUpViewModelMock(),
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
            uiState = SignUpUiState(),
            viewModel = SignUpViewModelMock(),
            prev = { },
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
private fun OptionalInformationStagePreview() {
    GeoMateTheme {
        OptionalInformationStage(
            uiState = SignUpUiState(),
            viewModel = SignUpViewModelMock(),
            next = { },
            prev = { }
        )
    }
}