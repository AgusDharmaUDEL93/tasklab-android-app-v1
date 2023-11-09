package com.udeldev.tasklab.presentation.home

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.udeldev.tasklab.domain.model.Todo
import com.udeldev.tasklab.presentation.home.components.CardTodoItem
import com.udeldev.tasklab.presentation.util.Screen
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {

    val search = state.searchItem
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(

        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                title = {
                    Row {
                        Text(
                            text = "Your",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " Labs",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.AboutScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "About page"
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddScreen.route + "/id=-1")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add todo button")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = search,
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search icon") },
                    onValueChange = { text ->
                        onEvent(HomeEvent.OnSearch(text))
                    },
                    label = {
                        Text(text = "Search")
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (state.todo.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Let's add your task !!",
                            fontSize = 18.sp,
                        )
                    }
                }

                LazyColumn {
                    items(items = state.todo, key = { it.id!! }) { todo ->
                        CardTodoItem(
                            modifier = Modifier
                                .animateItemPlacement(
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearOutSlowInEasing,
                                    )
                                )
                                .clickable {
                                    navController.navigate(Screen.DetailScreen.route + "/id=${todo.id}")
                                },
                            todo = todo,
                            onChecked = {
                                onEvent(
                                    HomeEvent.ToggleIsComplete(
                                        todo.copy(
                                            isComplete = !todo.isComplete
                                        )
                                    )
                                )
                            },
                            onDelete = {
                                onEvent(HomeEvent.DeleteTodo(todo))
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Todo deleted",
                                        actionLabel = "Undo",
                                        duration = SnackbarDuration.Short,
                                        withDismissAction = true,
                                    )

                                    when (result) {
                                        Dismissed -> {
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                        }

                                        ActionPerformed -> {
                                            onEvent(HomeEvent.RestoreTodo)
                                        }
                                    }
                                }
                            }
                        )

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(
            todo = listOf(
                Todo(title = "Blabla", deadline = LocalDate.now()),
                Todo(title = "Blabla", deadline = LocalDate.now()),
                Todo(title = "Blabla", deadline = LocalDate.now(), isComplete = true),
                Todo(title = "Blabla", deadline = LocalDate.now()),
                Todo(title = "Blabla", deadline = LocalDate.now())
            )
        ),
        onEvent = {},
        navController = rememberNavController()
    )
}