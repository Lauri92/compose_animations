package com.example.animation_experiments.navigation.destinations.lines

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animation_experiments.navigation.destinations.Screen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.LinesComposable(
    navigateHome: () -> Unit,
) {
    composable(
        route = Screen.Lines.route,
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
        LinesScreen(navigateHome = navigateHome)
    }
}