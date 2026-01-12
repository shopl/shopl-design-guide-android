package com.shopl.sdg.component.check_option.preview

import com.shopl.sdg.component.check_option.SDGCheckOptionColor
import com.shopl.sdg.component.check_option.SDGCheckOptionSize
import com.shopl.sdg.component.check_option.SDGCheckOptionStatus
import com.shopl.sdg.component.check_option.SDGCheckOptionType

internal data class SDGCheckOptionPreviewParams(
    val type: SDGCheckOptionType,
    val status: SDGCheckOptionStatus,
    val selectedColor: SDGCheckOptionColor,
    val size: SDGCheckOptionSize,
)
