package com.shopl.sdg.component.text_input.simple

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGSimpleTextInputState {
    data object Default : SDGSimpleTextInputState
    data object Completed : SDGSimpleTextInputState
    data object Disabled : SDGSimpleTextInputState
    data object Error : SDGSimpleTextInputState
}
