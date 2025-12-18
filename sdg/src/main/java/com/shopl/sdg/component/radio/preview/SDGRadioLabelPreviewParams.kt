package com.shopl.sdg.component.radio.preview

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.radio.SDGRadioLabelType
import com.shopl.sdg_common.foundation.SDGColor

internal data class SDGRadioLabelPreviewParams(
    val type: SDGRadioLabelType,
    val label: String,
    val isChecked: Boolean,
    val enabled: Boolean = true,
    val checkTextColor: Color = SDGColor.Neutral700,
)
