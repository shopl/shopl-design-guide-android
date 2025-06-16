package com.shopl.sdg.component.button.capsule

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class SDGCapsuleButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val gap: Dp,
    val radius: Dp,
    val labelSize: TextUnit,
) {

    data object Large : SDGCapsuleButtonSize(
        height = 50.dp,
        horizontalPadding = 20.dp,
        gap = 8.dp,
        radius = 25.dp,
        labelSize = 16.sp,
    )


    data object Medium : SDGCapsuleButtonSize(
        height = 36.dp,
        horizontalPadding = 12.dp,
        gap = 4.dp,
        radius = 18.dp,
        labelSize = 14.sp,
    )


    data object Small : SDGCapsuleButtonSize(
        height = 28.dp,
        horizontalPadding = 8.dp,
        gap = 2.dp,
        radius = 14.dp,
        labelSize = 12.sp,
    )


    data object XSmall : SDGCapsuleButtonSize(
        height = 20.dp,
        horizontalPadding = 6.dp,
        gap = 2.dp,
        radius = 10.dp,
        labelSize = 12.sp,
    )
}