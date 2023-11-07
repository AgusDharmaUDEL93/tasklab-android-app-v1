package com.udeldev.tasklab.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udeldev.tasklab.domain.model.Todo
import com.udeldev.tasklab.domain.use_case.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val todoUsesCases: TodoUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var recentlyDeletedTodo: Todo? = null

    private var getTodosJob: Job? = null

    init {
        getTodo("")
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.DeleteTodo -> {
                viewModelScope.launch {
                    recentlyDeletedTodo = event.todo
                    todoUsesCases.deleteTodoUseCase(event.todo)
                }
            }

            is HomeEvent.ToggleIsComplete -> {
                viewModelScope.launch {
                    todoUsesCases.addTodoUseCase(event.todo)
                }
            }

            is HomeEvent.OnSearch -> {
                _state.value = _state.value.copy(
                    searchItem = event.title
                )
                getTodo(event.title)
            }

            HomeEvent.RestoreTodo -> {
                viewModelScope.launch {
                    todoUsesCases.addTodoUseCase(recentlyDeletedTodo ?: return@launch)
                    recentlyDeletedTodo = null
                }
            }
        }
    }

    private fun getTodo(title: String) {
        getTodosJob?.cancel()
        getTodosJob = todoUsesCases.getTodosUseCase(title)
            .onEach { todos ->
                _state.value = _state.value.copy(
                    todo = todos,
                    searchItem = title
                )
            }.launchIn(viewModelScope)
    }

}