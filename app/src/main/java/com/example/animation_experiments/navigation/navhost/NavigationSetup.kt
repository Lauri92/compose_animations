package com.example.animation_experiments.navigation.navhost

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.animation_experiments.navigation.destinations.Screen
import com.example.animation_experiments.navigation.destinations.color.ColorComposable
import com.example.animation_experiments.navigation.destinations.graphics.GraphicsComposable
import com.example.animation_experiments.navigation.destinations.graphicslayer.GraphicsLayerComposable
import com.example.animation_experiments.navigation.destinations.home.HomeComposable
import com.example.animation_experiments.navigation.destinations.lines.LinesComposable
import com.example.animation_experiments.navigation.destinations.visibility.VisibilityComposable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationSetup(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        HomeComposable(
            navigateToLines = {
                navController.navigate(Screen.Lines.route)
            },
            navigateToVisibility = {
                navController.navigate(Screen.Visibility.route)
            },
            navigateToGraphicsLayer = {
                navController.navigate(Screen.GraphicsLayer.route)
            },
            navigateToColor = {
                navController.navigate(Screen.Color.route)
            },
            navigateToGraphics = {
                navController.navigate(Screen.Graphics.route)
            }
        )

        LinesComposable {
            navController.popBackStack()
        }

        VisibilityComposable {
            navController.popBackStack()
        }

        GraphicsLayerComposable {
            navController.popBackStack()
        }

        ColorComposable {
            navController.popBackStack()
        }

        GraphicsComposable {
            navController.popBackStack()
        }

    }
}