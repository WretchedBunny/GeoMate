package com.example.geomate.ui.screens.sign_in
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.ext.isValidEmail
import com.example.geomate.service.AccountService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

private const val TAG = "SignInViewModel"
class SignInViewModel : ViewModel(){
    private val accountService = AccountService(FirebaseAuth.getInstance())
    var uiState = mutableStateOf(SignInUIState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(){
        //TODO validate email textField
        if(!email.isValidEmail()){
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
        viewModelScope.launch{accountService.signIn(email, password)}

        //TODO Implement transfer to mapScreen
        Log.d(TAG, "The SignIn Was Successful")


    }
    fun onForgotPasswordClick(){
        //TODO handle transfer to "forgotPasswordScreen"
    }
}