package com.udeldev.tasklab.presentation.detail

import com.udeldev.tasklab.domain.model.Todo

sealed class DetailEvent {
    data object OnToggleExpand : DetailEvent()
    data class DeleteTodo(val todo: Todo) : DetailEvent()
}