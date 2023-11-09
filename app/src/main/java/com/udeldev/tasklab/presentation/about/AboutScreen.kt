package com.udeldev.tasklab.presentation.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.udeldev.tasklab.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.message
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navController: NavController,
    onEvent: (AboutEvent) -> Unit,
    state: AboutState,
) {

    val resultDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = resultDialogState,
        buttons = {
            positiveButton(
                text = "Okay",
                onClick = {
                    onEvent(AboutEvent.GetInitTodo)
                    Thread.sleep(1000)
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
            TopAppBar(
                modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                title = {

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
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = "Meet Our",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = " Developer!!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Avatar dev",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)// clip to the circle shape
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Putu Agus Dharma Kusuma",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold

            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "agusdharma48@gmail.com",
                fontSize = 16.sp,
            )
            Text(
                text = "Udayana University",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(25.dp))
//            Button(onClick = {
//                resultDialogState.show()
//            }) {
//                Text(text = "Get Todo")
//            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(
        navController = rememberNavController(),
        onEvent = {},
        state = AboutState()
    )
}