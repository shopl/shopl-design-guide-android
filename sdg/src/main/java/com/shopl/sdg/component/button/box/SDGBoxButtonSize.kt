package com.shopl.sdg.component.button.box

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.typography.SDGTypography

sealed class SDGBoxButtonSize(
    val height: Dp,
    val horizontalSpacing: Dp,
    val gap: Dp,
    val cornerRadius: Dp,
    val typography: SDGTypography,
) {
    data object Medium : SDGBoxButtonSize(
        height = 42.dp,
        horizontalSpacing = 16.dp,
        gap = 4.dp,
        cornerRadius = 12.dp,
        typography = SDGTypography.Body2R,
    )

    data object Small : SDGBoxButtonSize(
        height = 32.dp,
        horizontalSpacing = 10.dp,
        gap = 2.dp,
        cornerRadius = 8.dp,
        typography = SDGTypography.Body3R,
    )

    data object XSmall : SDGBoxButtonSize(
        height = 20.dp,
        horizontalSpacing = 6.dp,
        gap = 2.dp,
        cornerRadius = 6.dp,
        typography = SDGTypography.Body3R,
    )
}