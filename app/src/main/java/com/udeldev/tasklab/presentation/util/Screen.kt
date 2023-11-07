package com.udeldev.tasklab.presentation.util

sealed class Screen (val route : String) {
    data object HomeScreen : Screen("home")
    data object DetailScreen : Screen("detail")
    data object AddScreen : Screen("add")
    data object AboutScreen : Screen("about")
}