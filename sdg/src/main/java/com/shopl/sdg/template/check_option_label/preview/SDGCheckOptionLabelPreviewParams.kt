package com.shopl.sdg.template.check_option_label.preview

import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelSelectType
import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelSize
import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelState

internal data class SDGCheckOptionLabelPreviewParams(
    val state: SDGCheckOptionLabelState,
    val selectType: SDGCheckOptionLabelSelectType,
    val size: SDGCheckOptionLabelSize,
    val label: String,
)
