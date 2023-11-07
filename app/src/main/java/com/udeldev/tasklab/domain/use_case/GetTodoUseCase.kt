package com.udeldev.tasklab.domain.use_case

import com.udeldev.tasklab.domain.model.Todo
import com.udeldev.tasklab.domain.repository.TodoRepository

class GetTodoUseCase (
    private val repository: TodoRepository
) {
    suspend operator fun invoke(id : Int) : Todo?{
        return repository.getTodoById(id)
    }
}