package com.udeldev.tasklab.domain.use_case

data class TodoUseCases (
    val getTodoUseCase: GetTodoUseCase,
    val getTodosUseCase: GetTodosUseCase,
    val addTodoUseCase: AddTodoUseCase,
    val deleteTodoUseCase: DeleteTodoUseCase
)