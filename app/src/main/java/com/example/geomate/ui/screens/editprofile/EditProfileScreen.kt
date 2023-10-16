package com.example.geomate.ui.screens.editprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.PermContactCalendar
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.geomate.ext.isFirstNameValid
import com.example.geomate.ext.isLastNameValid
import com.example.geomate.ext.isUsernameValid
import com.example.geomate.ui.components.GeoMateFAB
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.InputValidator
import com.example.geomate.ui.components.TextFieldIcon
import com.example.geomate.ui.navigation.Destinations
import com.example.geomate.ui.theme.spacing
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

fun NavGraphBuilder.editProfile(
    viewModel: EditProfileViewModel,
    navController: NavController,
) {
    composable(Destinations.EDIT_PROFILE_ROUTE) {
        val uiState by viewModel.uiState.collectAsState()

        EditProfileScreen(
            uiState = uiState,
            viewModel = viewModel,
            navController = navController,
        )
    }
}

fun NavController.navigateToEditProfile() {
    navigate(Destinations.EDIT_PROFILE_ROUTE) {
        launchSingleTop = true
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    uiState: EditProfileUiState,
    viewModel: EditProfileViewModel,
    navController: NavController,
) {
    LaunchedEffect(Firebase.auth.uid) {
        Firebase.auth.uid?.let { uid ->
            viewModel.fetchUser(uid)
        }

    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.edit_profile_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navController::navigateUp) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        floatingActionButton = {
            GeoMateFAB(
                icon = Icons.Outlined.Check,
                containerColor = MaterialTheme.colorScheme.secondary,
                elevation = 2.dp
            ) {
                val isFirstNameValid = uiState.firstName.isFirstNameValid()
                viewModel.updateIsFirstNameValid(isFirstNameValid)

                val isLastNameValid = uiState.lastName.isLastNameValid()
                viewModel.updateIsLastNameValid(isLastNameValid)

                val isUsernameValid = uiState.username.isUsernameValid()
                viewModel.updateIsUsernameValid(isUsernameValid)

                val isEmailValid = uiState.email.isEmailValid()
                viewModel.updateIsEmailValid(isEmailValid)

                if (isFirstNameValid && isLastNameValid && isUsernameValid && isEmailValid) {
                    Firebase.auth.uid?.let { uid ->
                        viewModel.updateUser(uid)
                    }
                    navController.navigateUp()
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (uiState.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .padding(30.dp)
                .fillMaxWidth()
        ) {
            GeoMateTextField(
                value = uiState.firstName,
                onValueChange = viewModel::updateFirstName,
                leadingIcons = listOf(
                    TextFieldIcon {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = it,
                        )
                    }
                ),
                placeholder = stringResource(id = R.string.first_name_placeholder),
                inputValidator = InputValidator(
                    isValid = uiState.isFirstNameValid,
                    updateIsValid = viewModel::updateIsFirstNameValid,
                    rule = String::isFirstNameValid,
                    errorMessage = stringResource(id = R.string.invalid_firstname)
                )
            )
            GeoMateTextField(
                value = uiState.lastName,
                onValueChange = viewModel::updateLastName,
                leadingIcons = listOf(
                    TextFieldIcon {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = it,
                        )
                    }
                ),
                placeholder = stringResource(id = R.string.last_name_placeholder),
                inputValidator = InputValidator(
                    isValid = uiState.isLastNameValid,
                    updateIsValid = viewModel::updateIsLastNameValid,
                    rule = String::isLastNameValid,
                    errorMessage = stringResource(id = R.string.invalid_lastname)
                )
            )
            GeoMateTextField(
                value = uiState.username,
                onValueChange = viewModel::updateUsername,
                leadingIcons = listOf(
                    TextFieldIcon {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = it,
                        )
                    }
                ),
                placeholder = stringResource(id = R.string.username_placeholder),
                inputValidator = InputValidator(
                    isValid = uiState.isUsernameValid,
                    updateIsValid = viewModel::updateIsUsernameValid,
                    rule = String::isUsernameValid,
                    errorMessage = stringResource(id = R.string.invalid_username)
                )
            )
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
            GeoMateTextField(
                value = uiState.bio,
                onValueChange = viewModel::updateBio,
                leadingIcons = listOf(
                    TextFieldIcon {
                        Icon(
                            imageVector = Icons.Outlined.PermContactCalendar,
                            contentDescription = null,
                            modifier = it
                        )
                    }
                ),
                placeholder = stringResource(id = R.string.description_placeholder),
                supportingText = stringResource(id = R.string.optional),
            )
        }
    }
}