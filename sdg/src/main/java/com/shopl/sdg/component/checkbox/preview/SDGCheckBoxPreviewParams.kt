package com.shopl.sdg.component.checkbox.preview

import com.shopl.sdg.component.checkbox.model.SDGCheckBoxSelectedBackgroundColor
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxSize
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxState

internal data class SDGCheckBoxPreviewParams(
    val state: SDGCheckBoxState,
    val selectedColor: SDGCheckBoxSelectedBackgroundColor,
    val size: SDGCheckBoxSize,
)
