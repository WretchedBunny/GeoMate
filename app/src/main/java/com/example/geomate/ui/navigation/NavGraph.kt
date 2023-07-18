package com.example.geomate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.geomate.ui.screens.forgotpassword.forgotPassword
import com.example.geomate.ui.screens.signin.signIn
import com.example.geomate.ui.screens.signup.SignUpViewModel
import com.example.geomate.ui.screens.signup.signUp
import com.example.geomate.ui.theme.GeoMateTheme

@Composable
fun NavGraph(navController: NavHostController) {
    val signUpViewModel = SignUpViewModel()

    GeoMateTheme {
        NavHost(
            navController = navController,
            startDestination = Destinations.SIGN_IN_ROUTE
        ) {
            forgotPassword(navController = navController)
            signIn(navController = navController)
            signUp(
                navController = navController,
                viewModel = signUpViewModel
            )
        }
    }
}