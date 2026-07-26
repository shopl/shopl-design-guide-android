package com.shopl.sdg.component.text_input.fixed

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGFixedTextInputField {
    data object LightGray : SDGFixedTextInputField
    data object White : SDGFixedTextInputField
}
