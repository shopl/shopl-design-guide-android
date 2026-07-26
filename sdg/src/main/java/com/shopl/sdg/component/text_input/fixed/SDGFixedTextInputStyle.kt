package com.shopl.sdg.component.text_input.fixed

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGFixedTextInputStyle {
    data object Solid : SDGFixedTextInputStyle
    data object Outlined : SDGFixedTextInputStyle
}
