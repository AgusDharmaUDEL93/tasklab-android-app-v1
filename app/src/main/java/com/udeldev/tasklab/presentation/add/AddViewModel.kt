package com.udeldev.tasklab.presentation.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udeldev.tasklab.domain.model.InvalidTodoException
import com.udeldev.tasklab.domain.model.Todo
import com.udeldev.tasklab.domain.use_case.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AddState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("id")?.let { todoId ->
            if (todoId != -1) {
                viewModelScope.launch {
                    todoUseCases.getTodoUseCase(todoId)?.also { todo ->
                        _state.value = _state.value.copy(
                            title = todo.title,
                            description = todo.description,
                            deadline = todo.deadline,
                            id = todo.id
                        )
                    }
                }
            }

        }
    }

    fun onEvent(event: AddEvent) {
        when (event) {
            is AddEvent.AddTodo -> {
                viewModelScope.launch {
                    try {
                        todoUseCases.addTodoUseCase(
                            Todo(
                                id = if (_state.value.id != null) {
                                    _state.value.id
                                } else {
                                    null
                                },
                                title = _state.value.title,
                                description = _state.value.description,
                                deadline = _state.value.deadline!!
                            )
                        )
                    } catch (e: InvalidTodoException) {
                        _state.value = _state.value.copy(
                            resultDialogMessage = e.message.toString()
                        )
                    } catch (e: NullPointerException) {
                        _state.value = _state.value.copy(
                            resultDialogMessage = "Field can't be empty"
                        )
                    }
                }
            }

            is AddEvent.OnChangeDeadline -> {
                _state.value = _state.value.copy(
                    deadline = event.deadline
                )
            }

            is AddEvent.OnChangeDescription -> {
                _state.value = _state.value.copy(
                    description = event.description
                )
            }

            is AddEvent.OnChangeTitle -> {
                _state.value = _state.value.copy(
                    title = event.title
                )
            }

            AddEvent.OnToggleDatePickerDialog -> {
                _state.value = _state.value.copy(
                    isOpenDatePicker = !_state.value.isOpenDatePicker
                )
            }
        }
    }
}