package com.example.geomate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.geomate.ui.navigation.NavGraph
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pipelineConfig = OkHttpImagePipelineConfigFactory
            .newBuilder(this, OkHttpClient.Builder().build())
            .setDiskCacheEnabled(true)
            .setDownsampleEnabled(true)
            .setResizeAndRotateEnabledForNetwork(true)
            .build()
        Fresco.initialize(this, pipelineConfig)

        setContent {
            val navController = rememberNavController()
            NavGraph(application = application, navController = navController)
        }
    }
}