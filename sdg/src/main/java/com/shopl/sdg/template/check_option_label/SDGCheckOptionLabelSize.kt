package com.shopl.sdg.template.check_option_label

import com.shopl.sdg_common.foundation.typography.SDGTypography

enum class SDGCheckOptionLabelSize(val typography: SDGTypography) {
    SMALL(typography = SDGTypography.Body2R),
    MEDIUM(typography = SDGTypography.Body1R)
}