package com.example.animation_experiments.navigation.destinations.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animation_experiments.navigation.destinations.Screen

fun NavGraphBuilder.HomeComposable(
    navigateToLines: () -> Unit,
    navigateToVisibility: () -> Unit,
    navigateToGraphicsLayer: () -> Unit,
    navigateToColor: () -> Unit,
) {
    composable(
        route = Screen.Home.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(durationMillis = 300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(durationMillis = 300)
            )
        },
    ) {
        Scaffold { paddingValues ->
            HomeScreen(
                paddingValues = paddingValues,
                navigateToLines = navigateToLines,
                navigateToVisibility = navigateToVisibility,
                navigateToGraphicsLayer = navigateToGraphicsLayer,
                navigateToColor = navigateToColor
            )
        }
    }
}