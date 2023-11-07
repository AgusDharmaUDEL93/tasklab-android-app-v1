package com.udeldev.tasklab.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udeldev.tasklab.domain.model.Todo
import java.time.LocalDate


@Composable
fun CardTodoItem(
    modifier: Modifier = Modifier,
    todo: Todo,
    onChecked: (Boolean) -> Unit,
    onDelete: () -> Unit
) {


    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)

        ) {
            Checkbox(checked = todo.isComplete, onCheckedChange = onChecked)
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = todo.title,
                    style = if (todo.isComplete) {
                        TextStyle(textDecoration = TextDecoration.LineThrough)
                    } else {
                        TextStyle(textDecoration = TextDecoration.None)
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${todo.deadline}",
                    style = if (todo.isComplete) {
                        TextStyle(textDecoration = TextDecoration.LineThrough)
                    } else {
                        TextStyle(textDecoration = TextDecoration.None)
                    },
                    color = if (todo.deadline.isBefore(LocalDate.now()) && !todo.isComplete) {
                        Color.Red
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete todo",
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CardTodoItemPreview() {
    CardTodoItem(
        todo = Todo(
            title = "Belajar Dulu",
            deadline = LocalDate.now(),
            description = "Blablablablablabalbalal"
        ),
        onChecked = {},
        onDelete = {}
    )
}