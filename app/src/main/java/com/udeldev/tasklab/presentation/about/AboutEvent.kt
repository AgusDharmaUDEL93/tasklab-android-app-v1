package com.udeldev.tasklab.presentation.about

sealed class AboutEvent {
    data object GetInitTodo : AboutEvent()
}