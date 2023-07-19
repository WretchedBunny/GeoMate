package com.example.geomate.ui.screens.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.ext.isEmailValid
import com.example.geomate.service.AccountService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "SignInViewModel"

class SignInViewModel : ViewModel() {
    private val accountService = AccountService(FirebaseAuth.getInstance())
    private var _uiState = MutableStateFlow(SignInUIState())
    val uiState = _uiState.asStateFlow()


    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onSignInClick() {
        //TODO validate email textField
        if (!email.isEmailValid()) {
            //TODO Implement error notification to user
            Log.d(TAG, "The email is not valid. Email looks like this: ${email}")
            return
        }
        //TODO validate password textField
        if (password.isBlank()) {
            //TODO Implement error notification to user
            Log.d(TAG, "The password is empty. Password textField looks like: ${password}")
            return
        }

        /*
        TODO
        Implement SignIn and Account Firebase service
         */
        Log.d(TAG, "Trying to sign in")
        viewModelScope.launch { accountService.signIn(email, password) }

        //TODO Implement transfer to mapScreen
        Log.d(TAG, "The SignIn Was Successful")


    }

    fun onForgotPasswordClick() {
        //TODO handle transfer to "forgotPasswordScreen"
    }
}