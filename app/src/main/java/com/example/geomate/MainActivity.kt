package com.example.geomate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.geomate.ui.screens.signin.SignInScreen
import com.example.geomate.ui.screens.signin.SignInViewModel
import com.example.geomate.ui.theme.GeoMateTheme

private const val TAG = "MainActivity "
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = SignInViewModel()
            val uiState by viewModel.uiState.collectAsState()
            GeoMateTheme {
                // A surface container using the 'background' color from the theme
                SignInScreen(
                    uiState = uiState,
                    onSignInClick = viewModel::onSignInClick,
                    updateEmail = viewModel::updateEmail,
                    updatePassword = viewModel::updatePassword
                )
            }
        }
    }

}