package com.shopl.sdg.component.button.capsule

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.typography.SDGTypography

@Stable
sealed class SDGCapsuleButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val cornerRadius: RoundedCornerShape,
    val gap: Dp,
    val typography: SDGTypography,
    val defaultIconSize: Dp
) {
    abstract val sizeName: String

    data object Large : SDGCapsuleButtonSize(
        height = 50.dp,
        horizontalPadding = 20.dp,
        gap = 8.dp,
        cornerRadius = SDGCornerRadius.BoxRadius.Radius25,
        typography = SDGTypography.Body1R,
        defaultIconSize = 18.dp
    ) {
        override val sizeName = "Large"
    }


    data object Medium : SDGCapsuleButtonSize(
        height = 36.dp,
        horizontalPadding = 12.dp,
        gap = 4.dp,
        cornerRadius = SDGCornerRadius.BoxRadius.Radius18,
        typography = SDGTypography.Body2R,
        defaultIconSize = 16.dp
    ) {
        override val sizeName = "Medium"
    }


    data object Small : SDGCapsuleButtonSize(
        height = 28.dp,
        horizontalPadding = 8.dp,
        gap = 2.dp,
        cornerRadius = SDGCornerRadius.BoxRadius.Radius14,
        typography = SDGTypography.Body3R,
        defaultIconSize = 14.dp
    ) {
        override val sizeName = "Small"
    }


    data object XSmall : SDGCapsuleButtonSize(
        height = 20.dp,
        horizontalPadding = 6.dp,
        gap = 2.dp,
        cornerRadius = SDGCornerRadius.BoxRadius.Radius10,
        typography = SDGTypography.Body3R,
        defaultIconSize = 14.dp
    ) {
        override val sizeName = "XSmall"
    }
}
