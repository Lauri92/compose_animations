package com.example.animation_experiments.navigation.destinations

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Lines : Screen("lines")
    data object Visibility : Screen("visibility")
    data object GraphicsLayer : Screen("graphicsLayer")
    data object Color : Screen("color")
}
