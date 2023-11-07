package com.udeldev.tasklab.presentation.home

import com.udeldev.tasklab.domain.model.Todo

data class HomeState (
    val todo : List<Todo> = emptyList(),
    val searchItem : String = ""
)