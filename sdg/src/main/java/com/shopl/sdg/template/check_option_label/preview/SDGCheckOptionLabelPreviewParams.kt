package com.shopl.sdg.template.check_option_label.preview

import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelSelectedType
import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelSize
import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelState

internal data class SDGCheckOptionLabelPreviewParams(
    val state: SDGCheckOptionLabelState,
    val selectType: SDGCheckOptionLabelSelectedType,
    val size: SDGCheckOptionLabelSize,
    val label: String,
)
