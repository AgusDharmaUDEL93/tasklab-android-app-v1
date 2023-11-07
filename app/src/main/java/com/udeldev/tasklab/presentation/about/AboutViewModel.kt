package com.udeldev.tasklab.presentation.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udeldev.tasklab.domain.model.InvalidTodoException
import com.udeldev.tasklab.domain.model.Todo
import com.udeldev.tasklab.domain.use_case.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(AboutState())
    val state = _state.asStateFlow()


    fun onEvent(event: AboutEvent) {
        when (event) {
            AboutEvent.GetInitTodo -> {
                viewModelScope.launch {
                    try {
                        for (i in 1..15) {
                            todoUseCases.addTodoUseCase(
                                Todo(
                                    title = "Belajar $i",
                                    description = "Ini pake ngetest $i",
                                    deadline = LocalDate.now()
                                )
                            )
                        }
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
        }
    }
}