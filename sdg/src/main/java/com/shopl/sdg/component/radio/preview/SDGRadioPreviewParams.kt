package com.shopl.sdg.component.radio.preview

import com.shopl.sdg.component.radio.model.SDGRadioSelectedBackgroundColor
import com.shopl.sdg.component.radio.model.SDGRadioSize
import com.shopl.sdg.component.radio.model.SDGRadioState

internal data class SDGRadioPreviewParams(
    val state: SDGRadioState,
    val selectedColor: SDGRadioSelectedBackgroundColor,
    val size: SDGRadioSize,
)
