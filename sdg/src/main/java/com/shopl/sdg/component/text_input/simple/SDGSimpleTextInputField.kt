package com.shopl.sdg.component.text_input.simple

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGSimpleTextInputField {
    val fieldName: String

    data object LightGray : SDGSimpleTextInputField {
        override val fieldName = "LightGray"
    }

    data object White : SDGSimpleTextInputField {
        override val fieldName = "White"
    }
}
