package com.shopl.sdg.component.check_option.preview

import com.shopl.sdg.component.check_option.model.SDGCheckOptionSelectedBackgroundColor
import com.shopl.sdg.component.check_option.model.SDGCheckOptionSize
import com.shopl.sdg.component.check_option.model.SDGCheckOptionState
import com.shopl.sdg.component.check_option.model.SDGCheckOptionStyle

internal data class SDGCheckOptionPreviewParams(
    val state: SDGCheckOptionState,
    val selectedBackgroundColor: SDGCheckOptionSelectedBackgroundColor,
    val size: SDGCheckOptionSize,
    val style: SDGCheckOptionStyle,
)
