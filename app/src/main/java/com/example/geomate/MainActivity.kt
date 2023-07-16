package com.example.geomate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.geomate.ui.screens.sign_in.SignInScreen
import com.example.geomate.ui.theme.GeoMateTheme

private const val TAG = "MainActivity "
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GeoMateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Log.d(
                        TAG,
                        "Entering Sign IN Screen from MainActivity"
                    )
                    SignInScreen()
                }
            }
        }
    }

    /*
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in.
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            /*
            TODO Need to create Sign In Screen
             */
            return
        }
    }
     */
}