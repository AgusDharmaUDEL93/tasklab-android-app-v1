package com.udeldev.tasklab.presentation.add

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.udeldev.tasklab.ui.theme.TaskLabTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp(){
        composeTestRule.setContent {
            TaskLabTheme {
                val navController = rememberNavController()
                AddScreen(
                    navController = navController,
                    state = AddState(
                        title = "Test Title",
                        description = "Test Description",
                        deadline = null, // Modify as needed
                    ),
                    onEvent = {}
                )
            }
        }
    }

    @Test
    fun addTodoWithValidData() {

        // Pengujian: Memastikan judul "Set Your Task" ditampilkan
        composeTestRule.onNodeWithText("Set Your Task").assertIsDisplayed()

        // Pengujian: Klik tombol kembali (navigation icon)
        composeTestRule.onNodeWithContentDescription("Back topbar").performClick()

        // Pengujian: Memeriksa tampilan teks pada field deskripsi
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
    }
}