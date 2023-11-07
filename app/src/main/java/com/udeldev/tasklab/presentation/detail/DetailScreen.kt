package com.udeldev.tasklab.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.udeldev.tasklab.domain.model.Todo
import com.udeldev.tasklab.presentation.util.Screen
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.message
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    state: DetailState,
    onEvent: (DetailEvent) -> Unit
) {

    val deleteDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = deleteDialogState,
        buttons = {
            positiveButton(
                text = "Yes",
                onClick = {
                    onEvent(DetailEvent.DeleteTodo(state.todo!!))
                    navController.navigateUp()
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            negativeButton(
                "No",
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },
    ) {
        title(
            text = "Deleting task !",
        )
        message(
            text = "Are u sure wanna delete this task ?"
        )
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                title = {
                    Text(
                        text = "Todo Details",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = "Back topbar"
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = {
                            onEvent(DetailEvent.OnToggleExpand)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu details"
                            )
                        }
                        DropdownMenu(
                            expanded = state.isExpanded,
                            onDismissRequest = {
                                onEvent(DetailEvent.OnToggleExpand)
                            },
                        ) {
                            DropdownMenuItem(text = {
                                Text(text = "Edit Todo")
                            }, onClick = {
                                navController.navigateUp()
                                navController.navigate(Screen.AddScreen.route + "/id=${state.todo?.id}")
                            })
                            DropdownMenuItem(text = {
                                Text(text = "Delete Todo")
                            }, onClick = {
                                deleteDialogState.show()
                            })
                        }
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Text(
                    text = "${state.todo?.title}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFEBEBEB),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Icon calendar in detail",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "${state.todo?.deadline}",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
                Text(
                    text = "About this todo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = state.todo?.description ?: "No description",
                    fontSize = 18.sp,
                    color = Color.Gray
                )

            }
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        navController = rememberNavController(),
        state = DetailState(
            todo = Todo(
                id = 1,
                title = "Test",
                deadline = LocalDate.now(),
                description = "blablabla",
                isComplete = true
            ),
            isExpanded = true,
        ),
        onEvent = {}
    )
}