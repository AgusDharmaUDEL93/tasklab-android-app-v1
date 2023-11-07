package com.udeldev.tasklab.domain.use_case

import com.udeldev.tasklab.domain.model.InvalidTodoException
import com.udeldev.tasklab.domain.model.Todo
import com.udeldev.tasklab.domain.repository.TodoRepository

class AddTodoUseCase (
    private val repository: TodoRepository
) {

    @Throws(InvalidTodoException::class)
    suspend operator fun invoke(todo: Todo){
        if (todo.title.isBlank()){
            throw InvalidTodoException("Title can't empty")
        }
        if (todo.description.isBlank()){
            throw InvalidTodoException("Description can't empty")
        }
        if (todo.deadline.equals(null)){
            throw InvalidTodoException("Deadline can't empty")
        }
        repository.insertTodo(todo)
    }
}