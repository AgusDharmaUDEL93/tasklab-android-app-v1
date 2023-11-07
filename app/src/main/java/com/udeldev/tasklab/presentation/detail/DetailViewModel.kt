package com.udeldev.tasklab.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udeldev.tasklab.domain.use_case.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("id")?.let {todoId ->
            if (todoId != -1){
                viewModelScope.launch {
                    todoUseCases.getTodoUseCase(todoId)?.also {todo ->
                        _state.value = _state.value.copy(
                            todo = todo
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: DetailEvent){
        when (event){
            DetailEvent.OnToggleExpand -> {
                _state.value = _state.value.copy(
                    isExpanded = !_state.value.isExpanded
                )
            }

            is DetailEvent.DeleteTodo -> {
                viewModelScope.launch {
                    todoUseCases.deleteTodoUseCase(event.todo)
                }
            }


        }
    }
}