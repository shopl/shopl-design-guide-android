package com.shopl.sdg.component.button.ghost

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing10
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography

@Stable
sealed class SDGGhostButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val gap: Dp,
    val typography: SDGTypography,
    val rippleRadius: Dp,
) {
    abstract val sizeName: String

    data object Large : SDGGhostButtonSize(
        height = 42.dp,
        horizontalPadding = Spacing16,
        gap = Spacing6,
        typography = SDGTypography.Body1R,
        rippleRadius = 14.dp,
    ) {
        override val sizeName = "Large"
    }

    data object Medium : SDGGhostButtonSize(
        height = 36.dp,
        horizontalPadding = Spacing10,
        gap = Spacing4,
        typography = SDGTypography.Body2R,
        rippleRadius = 12.dp,
    ) {
        override val sizeName = "Medium"
    }

    data object Small : SDGGhostButtonSize(
        height = 28.dp,
        horizontalPadding = Spacing8,
        gap = Spacing2,
        typography = SDGTypography.Body3R,
        rippleRadius = 9.dp,
    ) {
        override val sizeName = "Small"
    }
}