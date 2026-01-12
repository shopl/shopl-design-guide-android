package com.shopl.sdg.template.radio_label.preview

import com.shopl.sdg.template.radio_label.SDGRadioLabelColor
import com.shopl.sdg.template.radio_label.SDGRadioLabelStatus

internal data class SDGRadioLabelPreviewParams(
    val status: SDGRadioLabelStatus,
    val labelColor: SDGRadioLabelColor,
    val label: String,
)
