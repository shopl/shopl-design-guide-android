package com.shopl.sdg.template.popup.center.preview

import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputState
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.popup.center.input.SDGInputCenterPopupShowInputLabel

internal data class SDGInputCenterPopupPreviewParameter(
    val title: String?,
    val description: String?,
    val buttonOption: SDGCenterPopupButtonOption,
    val showInputLabel: SDGInputCenterPopupShowInputLabel,
    val initialInput: String,
    val hint: String,
    val state: SDGFixedTextInputState,
)
