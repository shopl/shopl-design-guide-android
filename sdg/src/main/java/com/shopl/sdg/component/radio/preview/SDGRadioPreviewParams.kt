package com.shopl.sdg.component.radio.preview

import com.shopl.sdg.component.radio.SDGRadioColor
import com.shopl.sdg.component.radio.SDGRadioSize

internal data class SDGRadioPreviewParams(
    val isSelected: Boolean,
    val isEnabled: Boolean = true,
    val color: SDGRadioColor = SDGRadioColor.BASIC,
    val size: SDGRadioSize = SDGRadioSize.MEDIUM,
)
