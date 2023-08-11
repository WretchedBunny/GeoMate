package com.example.geomate.ui.navigation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.geomate.service.account.FirebaseAccountService
import com.example.geomate.service.storage.FirebaseStorageService
import com.example.geomate.ui.screens.forgotpassword.ForgotPasswordViewModelImpl
import com.example.geomate.ui.screens.forgotpassword.forgotPassword
import com.example.geomate.ui.screens.signin.SignInViewModelImpl
import com.example.geomate.ui.screens.signin.signIn
import com.example.geomate.ui.screens.signup.SignUpViewModelImpl
import com.example.geomate.ui.screens.signup.signUp
import com.example.geomate.ui.theme.GeoMateTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavGraph(navController: NavHostController) {
    val accountService = FirebaseAccountService(FirebaseAuth.getInstance())
    val storageService = FirebaseStorageService(FirebaseFirestore.getInstance())

    val signInViewModel = SignInViewModelImpl(accountService)
    val signUpViewModel = SignUpViewModelImpl(storageService)
    val forgotPasswordViewModel = ForgotPasswordViewModelImpl(accountService)

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
            forgotPassword(
                navController = navController,
                viewModel = forgotPasswordViewModel
            )
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