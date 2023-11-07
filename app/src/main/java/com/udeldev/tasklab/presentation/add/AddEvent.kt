package com.udeldev.tasklab.presentation.add

import java.time.LocalDate

sealed class AddEvent{
    data class OnChangeTitle(val title : String) : AddEvent()
    data class OnChangeDeadline(val deadline : LocalDate) : AddEvent()
    data class OnChangeDescription (val description : String) : AddEvent()
    data object OnToggleDatePickerDialog : AddEvent()
    data object AddTodo  :AddEvent()
}
