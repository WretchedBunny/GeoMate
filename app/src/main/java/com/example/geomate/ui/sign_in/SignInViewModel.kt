package com.example.geomate.ui.sign_in
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.geomate.common.ext.isValidEmail

private val TAG = "SignInViewModel"
class SignInViewModel : ViewModel(
){
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
        }
        //TODO validate password textField
        //TODO handle transfer to mapScreen
    }
    fun onForgotPasswordClick(){
        //TODO handle transfer to "forgotPasswordScreen"
    }
}