package com.shopl.sdg.template.checkbox_label.preview

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabelType
import com.shopl.sdg_common.foundation.SDGColor

internal data class SDGCheckboxLabelPreviewParams(
    val type: SDGCheckboxLabelType,
    val label: String,
    val isChecked: Boolean,
    val enabled: Boolean = true,
    val checkTextColor: Color = SDGColor.Neutral700,
)
