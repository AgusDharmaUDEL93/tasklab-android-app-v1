package com.udeldev.tasklab.domain.repository

import com.udeldev.tasklab.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getAllTodo (): Flow<List<Todo>>

    suspend fun getTodoById(id :Int) : Todo?

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)
}