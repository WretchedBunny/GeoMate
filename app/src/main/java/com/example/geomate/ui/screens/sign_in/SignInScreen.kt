package com.example.geomate.ui.screens.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.geomate.ui.components.GeoMateTextField
import com.example.geomate.ui.components.Header
import com.example.geomate.ui.components.SupportingButton


@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = remember { SignInViewModel() }
){
    val uiState by viewModel.uiState
Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header(
            title = "Welcome back",
            subtitle = "We happy to see you again. To use your account, you should log in in first."
        )
        GeoMateTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            leadingIcon = Icons.Outlined.Email,
            placeholder = "Enter your email"
        )
        GeoMateTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = Icons.Outlined.Visibility,
            placeholder = "Enter your password",
            supportingButton = SupportingButton(
                text = "Forgot password?",
                action = { viewModel.onForgotPasswordClick() }
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {viewModel.onSignInClick()}
        ){
            Text("Sign In")
        }
    }
}

