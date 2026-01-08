package com.shopl.sdg.template.radio_label.preview

import com.shopl.sdg.template.radio_label.SDGRadioLabelColor

internal data class SDGRadioLabelPreviewParams(
    val isSelected: Boolean,
    val isEnabled: Boolean,
    val labelColor: SDGRadioLabelColor,
    val label: String,
)
