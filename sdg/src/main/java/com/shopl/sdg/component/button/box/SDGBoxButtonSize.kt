package com.shopl.sdg.component.button.box

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class SDGBoxButtonSize(
    val height: Dp,
    val horizontalSpacing: Dp,
    val gap: Dp,
    val cornerRadius: Dp,
    val labelSize: TextUnit,
) {
    data object Medium : SDGBoxButtonSize(
        height = 42.dp,
        horizontalSpacing = 16.dp,
        gap = 4.dp,
        cornerRadius = 12.dp,
        labelSize = 14.sp,
    )

    data object Small : SDGBoxButtonSize(
        height = 32.dp,
        horizontalSpacing = 10.dp,
        gap = 2.dp,
        cornerRadius = 8.dp,
        labelSize = 12.sp,
    )

    data object XSmall : SDGBoxButtonSize(
        height = 20.dp,
        horizontalSpacing = 6.dp,
        gap = 2.dp,
        cornerRadius = 6.dp,
        labelSize = 12.sp,
    )
}