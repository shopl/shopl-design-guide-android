package com.shopl.sdg.component.checkbox.preview

import com.shopl.sdg.component.checkbox.model.SDGCheckBoxSelectedColor
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxSize
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxState

internal data class SDGCheckBoxPreviewParams(
    val state: SDGCheckBoxState,
    val selectedColor: SDGCheckBoxSelectedColor,
    val size: SDGCheckBoxSize,
)
