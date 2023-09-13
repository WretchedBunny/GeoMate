package com.example.geomate.ui.navigation

import android.app.Application
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.geomate.service.account.FirebaseAccountService
import com.example.geomate.service.bucket.FirebaseBucketService
import com.example.geomate.service.storage.FirebaseStorageService
import com.example.geomate.ui.screens.forgotpassword.ForgotPasswordViewModelImpl
import com.example.geomate.ui.screens.forgotpassword.forgotPassword
import com.example.geomate.ui.screens.groupdetails.groupDetails
import com.example.geomate.ui.screens.groups.GroupsViewModelImpl
import com.example.geomate.ui.screens.groups.groups
import com.example.geomate.ui.screens.map.MapViewModelImpl
import com.example.geomate.ui.screens.map.map
import com.example.geomate.ui.screens.signin.SignInViewModelImpl
import com.example.geomate.ui.screens.signin.signIn
import com.example.geomate.ui.screens.signup.SignUpViewModelImpl
import com.example.geomate.ui.screens.signup.signUp
import com.example.geomate.ui.theme.GeoMateTheme
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@Composable
fun NavGraph(application: Application, navController: NavHostController) {
    // Services
    val storageService = FirebaseStorageService(FirebaseFirestore.getInstance())
    val accountService = FirebaseAccountService(FirebaseAuth.getInstance(), storageService)
    val bucketService = FirebaseBucketService(FirebaseStorage.getInstance())

    // ViewModels and UiStates
    val forgotPasswordViewModel = ForgotPasswordViewModelImpl(accountService)
    val forgotPasswordUiState by forgotPasswordViewModel.uiState.collectAsState()

    val signInViewModel = SignInViewModelImpl(storageService, bucketService)
    val signInUiState by signInViewModel.uiState.collectAsState()

    val signUpViewModel = SignUpViewModelImpl(storageService, bucketService)
    val signUpUiState by signUpViewModel.uiState.collectAsState()

    val mapViewModel = MapViewModelImpl(
        application,
        storageService,
        bucketService,
        LocationServices.getFusedLocationProviderClient(application.applicationContext)
    )
    val mapUiState by mapViewModel.uiState.collectAsState()

    val groupViewModel = GroupsViewModelImpl(bucketService)
    val groupUiState by groupViewModel.uiState.collectAsState()

    val startDestination = when (FirebaseAuth.getInstance().currentUser) {
        null -> Destinations.SIGN_IN_ROUTE
        else -> Destinations.MAP_ROUTE
    }
    GeoMateTheme {
        NavHost(
            navController = navController,
            startDestination = Destinations.GROUPS_ROUTE,
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
            forgotPassword(forgotPasswordUiState, forgotPasswordViewModel, navController)
            signIn(signInUiState, signInViewModel, navController)
            signUp(signUpUiState, signUpViewModel, navController)
            map(mapUiState, mapViewModel, navController)
            groups(groupUiState, groupViewModel, navController)
            groupDetails()
        }
    }
}