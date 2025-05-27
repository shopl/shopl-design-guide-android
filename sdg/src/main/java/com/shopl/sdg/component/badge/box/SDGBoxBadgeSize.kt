package com.shopl.sdg.component.badge.box

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.typography.SDGTypography

/**
 * [SDGBoxBadge] Size
 */
sealed class SDGBoxBadgeSize(
    val radius: Dp,
    val verticalPadding: Dp,
    val horizontalPadding: Dp,
    val iconGap: Dp,
    val typography: SDGTypography
) {
    data object XSmall : SDGBoxBadgeSize(
        radius = 6.dp,
        verticalPadding = 2.dp,
        horizontalPadding = 6.dp,
        iconGap = 4.dp,
        typography = SDGTypography.Body3R,
    )
}