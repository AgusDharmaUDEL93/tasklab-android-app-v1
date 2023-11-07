package com.udeldev.tasklab.presentation.about

import com.udeldev.tasklab.domain.model.Todo

data class AboutState (
    val todos : List<Todo> = emptyList(),
    val resultDialogMessage : String = "Testing todo added"
)