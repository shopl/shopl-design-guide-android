package com.shopl.sdg.template.checkbox_label.preview

import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabelSelectType
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabelState

internal data class SDGCheckboxLabelPreviewParams(
    val state: SDGCheckboxLabelState,
    val selectType: SDGCheckboxLabelSelectType,
    val label: String,
)
