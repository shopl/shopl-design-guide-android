package com.shopl.sdg.component.text_input.fixed

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGFixedTextInputStyle {
    val styleName: String

    data object Solid : SDGFixedTextInputStyle {
        override val styleName = "Solid"
    }

    data object Outlined : SDGFixedTextInputStyle {
        override val styleName = "Outlined"
    }
}
