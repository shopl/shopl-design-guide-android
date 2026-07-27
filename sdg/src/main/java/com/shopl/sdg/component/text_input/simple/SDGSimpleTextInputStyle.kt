package com.shopl.sdg.component.text_input.simple

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGSimpleTextInputStyle {
    val styleName: String

    data object Solid : SDGSimpleTextInputStyle {
        override val styleName = "Solid"
    }

    data object Outlined : SDGSimpleTextInputStyle {
        override val styleName = "Outlined"
    }
}
