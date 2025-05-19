package com.shopl.sdg.component.badge.capsule

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.typography.SDGTypography

/**
 * [SDGCapsuleBadge] Size
 */
sealed class SDGCapsuleBadgeSize(
    val verticalPadding: Dp,
    val radius: Dp,
    val horizontalPadding: Dp,
    val iconGap: Dp,
    val typography: SDGTypography,
) {
    //TODO: 디자인 시스템 내 제거 예정
    data object Medium : SDGCapsuleBadgeSize(
        radius = 18.dp,
        verticalPadding = 36.dp,
        horizontalPadding = 12.dp,
        iconGap = 8.dp,
        typography = SDGTypography.Body2R,
    )

    data object Small : SDGCapsuleBadgeSize(
        radius = 15.dp,
        verticalPadding = 6.dp,
        horizontalPadding = 10.dp,
        iconGap = 2.dp,
        typography = SDGTypography.Body2R,
    )

    data object XSmall : SDGCapsuleBadgeSize(
        radius = 10.dp,
        verticalPadding = 2.dp,
        horizontalPadding = 8.dp,
        iconGap = 2.dp,
        typography = SDGTypography.Body3R,
    )
}