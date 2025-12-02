package com.shopl.sdg.component.button.capsule

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.foundation.SDGCornerRadius

sealed class SDGCapsuleButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val cornerRadius: RoundedCornerShape,
    val gap: Dp,
    val typography: TextUnit,
) {

    data object Large : SDGCapsuleButtonSize(
        height = 50.dp,
        horizontalPadding = 20.dp,
        gap = 8.dp,
        cornerRadius = SDGCornerRadius.BoxRadius.Radius25,
        typography = 16.sp,
    )


    data object Medium : SDGCapsuleButtonSize(
        height = 36.dp,
        horizontalPadding = 12.dp,
        gap = 4.dp,
        cornerRadius = SDGCornerRadius.BoxRadius.Radius18,
        typography = 14.sp,
    )


    data object Small : SDGCapsuleButtonSize(
        height = 28.dp,
        horizontalPadding = 8.dp,
        gap = 2.dp,
        cornerRadius = SDGCornerRadius.BoxRadius.Radius14,
        typography = 12.sp,
    )


    data object XSmall : SDGCapsuleButtonSize(
        height = 20.dp,
        horizontalPadding = 6.dp,
        gap = 2.dp,
        cornerRadius = SDGCornerRadius.BoxRadius.Radius10,
        typography = 12.sp,
    )
}