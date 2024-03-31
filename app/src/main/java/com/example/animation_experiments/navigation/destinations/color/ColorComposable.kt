package com.example.animation_experiments.navigation.destinations.color

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animation_experiments.navigation.destinations.Screen

@Suppress("FunctionName")
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.ColorComposable(
    navigateHome: () -> Unit,
) {
    composable(
        route = Screen.Color.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(durationMillis = 300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(durationMillis = 300)
            )
        },
    ) {
        ColorScreen(navigateHome = navigateHome)
    }
}