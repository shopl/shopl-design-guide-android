package com.shopl.sdg.component.button.box

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class SDGBoxButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val gap: Dp,
    val radius: Dp,
    val labelSize: TextUnit,
) {
    data object Medium : SDGBoxButtonSize(
        height = 42.dp,
        horizontalPadding = 16.dp,
        gap = 4.dp,
        radius = 12.dp,
        labelSize = 14.sp,
    )

    data object Small : SDGBoxButtonSize(
        height = 32.dp,
        horizontalPadding = 10.dp,
        gap = 2.dp,
        radius = 8.dp,
        labelSize = 12.sp,
    )

    data object XSmall : SDGBoxButtonSize(
        height = 20.dp,
        horizontalPadding = 6.dp,
        gap = 2.dp,
        radius = 6.dp,
        labelSize = 12.sp,
    )
}