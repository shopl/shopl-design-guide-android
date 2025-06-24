package com.shopl.sdg.component.button.ghost

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class SDGGhostButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val gap: Dp,
    val labelSize: TextUnit,
    val rippleRadius: Dp,
) {
    data object Large : SDGGhostButtonSize(
        height = 42.dp,
        horizontalPadding = 16.dp,
        gap = 6.dp,
        labelSize = 16.sp,
        rippleRadius = 14.dp,
    )

    data object Medium : SDGGhostButtonSize(
        height = 36.dp,
        horizontalPadding = 10.dp,
        gap = 4.dp,
        labelSize = 14.sp,
        rippleRadius = 12.dp,
    )

    data object Small : SDGGhostButtonSize(
        height = 28.dp,
        horizontalPadding = 6.dp,
        gap = 2.dp,
        labelSize = 12.sp,
        rippleRadius = 9.dp,
    )
}