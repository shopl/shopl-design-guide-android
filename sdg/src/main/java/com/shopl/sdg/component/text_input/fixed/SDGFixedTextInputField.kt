package com.shopl.sdg.component.text_input.fixed

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGFixedTextInputField {
    val fieldName: String

    data object LightGray : SDGFixedTextInputField {
        override val fieldName = "LightGray"
    }

    data object White : SDGFixedTextInputField {
        override val fieldName = "White"
    }
}
