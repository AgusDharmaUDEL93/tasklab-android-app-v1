package com.udeldev.tasklab.presentation.add

import java.time.LocalDate

data class AddState(
    val id : Int? = null,
    val title : String = "",
    val description : String = "",
    val deadline : LocalDate? = null,
    val isOpenDatePicker : Boolean = false,
    val resultDialogMessage : String = "Todo saved !!"
)
