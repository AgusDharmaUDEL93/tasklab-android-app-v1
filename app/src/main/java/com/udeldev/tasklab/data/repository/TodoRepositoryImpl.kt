package com.udeldev.tasklab.data.repository

import com.udeldev.tasklab.data.source.TodoDao
import com.udeldev.tasklab.domain.model.Todo
import com.udeldev.tasklab.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl (
    private val todoDao: TodoDao
) :TodoRepository {
    override fun getAllTodo(): Flow<List<Todo>> {
        return todoDao.getAllTodo()
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return todoDao.getTodoById(id)
    }

    override suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }
}