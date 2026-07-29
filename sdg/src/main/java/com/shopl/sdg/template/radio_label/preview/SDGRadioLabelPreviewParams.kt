package com.shopl.sdg.template.radio_label.preview

import com.shopl.sdg.template.radio_label.SDGRadioLabelSelectType
import com.shopl.sdg.template.radio_label.SDGRadioLabelState

internal data class SDGRadioLabelPreviewParams(
    val state: SDGRadioLabelState,
    val selectType: SDGRadioLabelSelectType,
    val label: String,
)
