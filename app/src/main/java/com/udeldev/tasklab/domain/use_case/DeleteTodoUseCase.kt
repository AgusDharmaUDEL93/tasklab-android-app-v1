package com.udeldev.tasklab.domain.use_case

import com.udeldev.tasklab.domain.model.Todo
import com.udeldev.tasklab.domain.repository.TodoRepository

class DeleteTodoUseCase (
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo){
        repository.deleteTodo(todo)
    }
}