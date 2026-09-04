package com.shopl.sdg.component.badge.box

import com.shopl.sdg_common.foundation.typography.SDGTypography

enum class SDGBoxBadgeFontWeight(
    internal val typography: SDGTypography,
) {
    Normal(typography = SDGTypography.Body3R),
    Bold(typography = SDGTypography.Body3SB),
}
