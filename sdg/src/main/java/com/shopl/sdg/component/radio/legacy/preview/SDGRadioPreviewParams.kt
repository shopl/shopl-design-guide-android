package com.shopl.sdg.component.radio.legacy.preview

import com.shopl.sdg.component.radio.legacy.model.SDGRadioColor
import com.shopl.sdg.component.radio.legacy.model.SDGRadioSize
import com.shopl.sdg.component.radio.legacy.model.SDGRadioStatus

internal data class SDGRadioPreviewParams(
    val status: SDGRadioStatus,
    val color: SDGRadioColor,
    val size: SDGRadioSize,
)
