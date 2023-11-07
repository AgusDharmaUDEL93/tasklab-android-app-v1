package com.udeldev.tasklab.domain.use_case

import com.udeldev.tasklab.domain.model.Todo
import com.udeldev.tasklab.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodosUseCase (
    private val repository: TodoRepository
) {
    operator fun invoke(
        title : String = ""
    ) : Flow<List<Todo>> {
        return repository.getAllTodo().map {todo ->
            if (title.isNotEmpty()){
                todo.filter { it.title.lowercase().contains(title.lowercase()) }
            }else{
                todo.sortedBy { it.isComplete }
            }
        }
    }
}