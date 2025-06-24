package com.shopl.sdg.component.text_input

sealed class InputState {
    data object Enable : InputState()
    data object Disable : InputState()
    data class Error(val message: String) : InputState()
}