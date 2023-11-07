package com.udeldev.tasklab.presentation.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.message
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navController: NavController,
    state: AddState,
    onEvent: (AddEvent) -> Unit
) {
    val dateDialogState = rememberMaterialDialogState()
    val resultDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(
                text = "Okay",
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            negativeButton(
                text = "Cancel",
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },

        ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick Your Deadline",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.primary,
                dateActiveBackgroundColor = MaterialTheme.colorScheme.primary
            )
        ) { date ->
            onEvent(AddEvent.OnChangeDeadline(date))
        }
    }

    MaterialDialog(
        dialogState = resultDialogState,
        buttons = {
            positiveButton(
                text = "Okay",
                onClick = {
                    navController.navigateUp()
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },
    ) {
        title(
            text = "Result",
        )
        message(
            text = state.resultDialogMessage
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                title = {
                    Text(
                        text = "Set Your Task",
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
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.title,
                    onValueChange = { text ->
                        onEvent(AddEvent.OnChangeTitle(text))
                    },
                    label = {
                        Text(text = "Title")
                    }
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "Deadline")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        IconButton(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFEBEBEB),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(8.dp),
                            onClick = {
                                dateDialogState.show()
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Icon calendar in detail",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = if (state.deadline == null) {
                                "Deadline Not Set"
                            } else {
                                "${state.deadline}"
                            },
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                    }
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    value = state.description,
                    onValueChange = { text ->
                        onEvent(AddEvent.OnChangeDescription(text))
                    },
                    label = {
                        Text(text = "Description")
                    }
                )
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(150.dp),
                    onClick = {
                        onEvent(AddEvent.AddTodo)
                        resultDialogState.show()
                    }
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@Preview
@Composable
fun AddScreenPreview() {
    AddScreen(
        navController = rememberNavController(),
        state = AddState(
            title = "Blabla",
            description = "Ini deskripsi",
            deadline = LocalDate.now(),
            isOpenDatePicker = true,
        ),
        onEvent = {}
    )
}