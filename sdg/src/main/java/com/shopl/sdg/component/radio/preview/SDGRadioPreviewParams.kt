package com.shopl.sdg.component.radio.preview

import com.shopl.sdg.component.radio.SDGRadioColor
import com.shopl.sdg.component.radio.SDGRadioSize
import com.shopl.sdg.component.radio.SDGRadioStatus

internal data class SDGRadioPreviewParams(
    val status: SDGRadioStatus,
    val color: SDGRadioColor,
    val size: SDGRadioSize,
)
