package com.shopl.sdg.template.check_option_label

import androidx.compose.ui.unit.Dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing1
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography

enum class SDGCheckOptionLabelSize(
    val typography: SDGTypography,
    internal val gap: Dp,
    internal val checkOptionVerticalPadding: Dp,
) {
    SMALL(
        typography = SDGTypography.Body2R,
        gap = Spacing6,
        checkOptionVerticalPadding = Spacing1,
    ),
    MEDIUM(
        typography = SDGTypography.Body1R,
        gap = Spacing8,
        checkOptionVerticalPadding = Spacing2,
    ),
}
