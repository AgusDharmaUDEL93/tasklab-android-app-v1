package com.udeldev.tasklab.presentation.detail

import com.udeldev.tasklab.domain.model.Todo

data class DetailState (
    val todo: Todo? = null,
    val isExpanded : Boolean = false,
)