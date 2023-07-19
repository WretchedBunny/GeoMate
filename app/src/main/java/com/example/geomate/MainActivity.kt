package com.example.geomate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.geomate.ui.screens.signup.SignUpScreen
import com.example.geomate.ui.screens.signup.SignUpViewModel
import com.example.geomate.ui.theme.GeoMateTheme

private const val TAG = "MainActivity "

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = SignUpViewModel()
            val uiState by viewModel.uiState.collectAsState()
            GeoMateTheme {
                // A surface container using the 'background' color from the theme
                SignUpScreen(
                    uiState = uiState,
                    updateEmail = viewModel::updateEmail,
                    updatePassword = viewModel::updatePassword,
                    updateFirstName = viewModel::updateFirstName,
                    updateLastName = viewModel::updateLastName,
                    updateUsername = viewModel::updateUsername,
                    updateProfilePictureUri = viewModel::updateProfilePictureUri,
                    updateDescription = viewModel::updateDescription,
                    onContinueClick = viewModel::onContinueClick
                )
            }
        }
    }

}