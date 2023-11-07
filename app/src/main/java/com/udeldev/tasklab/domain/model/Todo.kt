package com.udeldev.tasklab.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Todo (
    @PrimaryKey
    val id : Int? = null,
    val title : String,
    val deadline : LocalDate,
    val description : String = "",
    val isComplete : Boolean = false,
)

class InvalidTodoException(message:String) : Exception(message)