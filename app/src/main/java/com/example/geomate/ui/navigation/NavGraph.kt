package com.example.geomate.ui.navigation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.geomate.ui.screens.forgotpassword.forgotPassword
import com.example.geomate.ui.screens.signin.SignInViewModel
import com.example.geomate.ui.screens.signin.signIn
import com.example.geomate.ui.screens.signup.SignUpViewModel
import com.example.geomate.ui.screens.signup.signUp
import com.example.geomate.ui.theme.GeoMateTheme

@Composable
fun NavGraph(navController: NavHostController) {
    val signInViewModel = SignInViewModel()
    val signUpViewModel = SignUpViewModel()

    GeoMateTheme {
        NavHost(
            navController = navController,
            startDestination = Destinations.SIGN_IN_ROUTE,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(0, easing = LinearEasing),
                    initialAlpha = 1f
                )
            },
            exitTransition = {
                fadeOut(tween(0, easing = LinearEasing))
            }
        ) {
            forgotPassword(navController = navController)
            signIn(
                navController = navController,
                viewModel = signInViewModel
            )
            signUp(
                navController = navController,
                viewModel = signUpViewModel
            )
        }
    }
}