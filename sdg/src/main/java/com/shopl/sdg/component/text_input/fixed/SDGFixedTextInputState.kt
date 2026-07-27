package com.shopl.sdg.component.text_input.fixed

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGFixedTextInputState {
    data object Default : SDGFixedTextInputState
    data object Completed : SDGFixedTextInputState
    data object Disabled : SDGFixedTextInputState
    data object Error : SDGFixedTextInputState
}
