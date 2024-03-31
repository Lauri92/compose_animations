package com.example.animation_experiments

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.example.animation_experiments.navigation.navhost.NavigationSetup
import com.example.animation_experiments.ui.theme.Animation_experimentsTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Animation_experimentsTheme {

                val navController = rememberNavController()

                NavigationSetup(navController = navController)

            }
        }
    }
}