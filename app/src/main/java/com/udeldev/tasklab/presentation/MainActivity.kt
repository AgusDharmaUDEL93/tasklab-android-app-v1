package com.udeldev.tasklab.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udeldev.tasklab.presentation.about.AboutScreen
import com.udeldev.tasklab.presentation.about.AboutViewModel
import com.udeldev.tasklab.presentation.add.AddScreen
import com.udeldev.tasklab.presentation.add.AddViewModel
import com.udeldev.tasklab.presentation.detail.DetailScreen
import com.udeldev.tasklab.presentation.detail.DetailViewModel
import com.udeldev.tasklab.presentation.home.HomeScreen
import com.udeldev.tasklab.presentation.home.HomeViewModel
import com.udeldev.tasklab.presentation.util.Screen
import com.udeldev.tasklab.ui.theme.TaskLabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskLabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFEFEFE)
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(route = Screen.HomeScreen.route) {
                            val viewModel = hiltViewModel<HomeViewModel>()
                            val state by viewModel.state.collectAsState()
                            HomeScreen(
                                state = state,
                                onEvent = viewModel::onEvent,
                                navController = navController
                            )
                        }
                        composable(
                            route = Screen.AddScreen.route + "/id={id}",
                            arguments = listOf(
                                navArgument(
                                    name = "id"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val viewModel = hiltViewModel<AddViewModel>()
                            val state by viewModel.state.collectAsState()
                            AddScreen(
                                navController = navController,
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable(route = Screen.AboutScreen.route) {
                            val viewModel = hiltViewModel<AboutViewModel>()
                            val state by viewModel.state.collectAsState()
                            AboutScreen(
                                navController = navController,
                                onEvent = viewModel::onEvent,
                                state = state
                            )
                        }
                        composable(
                            route = Screen.DetailScreen.route + "/id={id}",
                            arguments = listOf(
                                navArgument(
                                    name = "id"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val viewModel = hiltViewModel<DetailViewModel>()
                            val state by viewModel.state.collectAsState()
                            DetailScreen(
                                navController = navController,
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}
