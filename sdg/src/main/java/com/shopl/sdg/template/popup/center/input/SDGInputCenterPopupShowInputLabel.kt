package com.shopl.sdg.template.popup.center.input

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.AnnotatedString

@Immutable
sealed interface SDGInputCenterPopupShowInputLabel {
    data class True(val text: AnnotatedString) : SDGInputCenterPopupShowInputLabel {
        constructor(text: String) : this(AnnotatedString(text))
    }

    data object False : SDGInputCenterPopupShowInputLabel
}
