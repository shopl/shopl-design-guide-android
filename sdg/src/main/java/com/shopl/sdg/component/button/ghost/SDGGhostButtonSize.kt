package com.shopl.sdg.component.button.ghost

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.typography.SDGTypography

sealed class SDGGhostButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val gap: Dp,
    val typography: SDGTypography,
    val rippleRadius: Dp,
) {
    data object Large : SDGGhostButtonSize(
        height = 42.dp,
        horizontalPadding = 16.dp,
        gap = 6.dp,
        typography = SDGTypography.Body1R,
        rippleRadius = 14.dp,
    )

    data object Medium : SDGGhostButtonSize(
        height = 36.dp,
        horizontalPadding = 10.dp,
        gap = 4.dp,
        typography = SDGTypography.Body2R,
        rippleRadius = 12.dp,
    )

    data object Small : SDGGhostButtonSize(
        height = 28.dp,
        horizontalPadding = 8.dp,
        gap = 2.dp,
        typography = SDGTypography.Body3R,
        rippleRadius = 9.dp,
    )
}