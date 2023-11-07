package com.udeldev.tasklab.presentation.home

import com.udeldev.tasklab.domain.model.Todo

sealed class HomeEvent{
    data class DeleteTodo(val todo: Todo) : HomeEvent()
    data class ToggleIsComplete(val todo: Todo ) : HomeEvent()
    data class OnSearch(val title :String) : HomeEvent()
    data object RestoreTodo : HomeEvent()
}