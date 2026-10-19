package com.shopl.sdg.template.popup.center.input

import androidx.compose.runtime.Immutable

/** Input Center Popup의 입력 라벨 표시 여부입니다. */
@Immutable
sealed interface SDGInputCenterPopupShowInputLabel {
    data class True(val text: String) : SDGInputCenterPopupShowInputLabel
    data object False : SDGInputCenterPopupShowInputLabel
}
