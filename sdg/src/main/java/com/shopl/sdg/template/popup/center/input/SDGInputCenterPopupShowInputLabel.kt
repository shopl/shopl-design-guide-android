package com.shopl.sdg.template.popup.center.input

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SDGInputCenterPopupShowInputLabel {
    data class True(val text: String) : SDGInputCenterPopupShowInputLabel
    data object False : SDGInputCenterPopupShowInputLabel
}
