package com.shopl.sdg.component.radio.preview

import com.shopl.sdg.component.radio.SDGRadioColor
import com.shopl.sdg.component.radio.SDGRadioSize

data class SDGRadioPreviewParams(
    val isSelected: Boolean,
    val color: SDGRadioColor = SDGRadioColor.BASIC,
    val size: SDGRadioSize = SDGRadioSize.MEDIUM,
)
