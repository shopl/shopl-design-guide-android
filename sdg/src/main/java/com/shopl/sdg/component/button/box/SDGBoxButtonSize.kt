package com.shopl.sdg.component.button.box

import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing10
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.typography.SDGTypography

sealed class SDGBoxButtonSize(
    val height: Dp,
    val horizontalSpacing: Dp,
    val iconGap: Dp,
    val shape: Shape,
    val typography: SDGTypography,
) {
    data object Medium : SDGBoxButtonSize(
        height = 42.dp,
        horizontalSpacing = Spacing16,
        iconGap = 4.dp,
        shape = SDGCornerRadius.BoxRadius.Radius12,
        typography = SDGTypography.Body2R,
    )

    data object Small : SDGBoxButtonSize(
        height = 32.dp,
        horizontalSpacing = Spacing10,
        iconGap = 2.dp,
        shape = SDGCornerRadius.BoxRadius.Radius8,
        typography = SDGTypography.Body3R,
    )

    data object XSmall : SDGBoxButtonSize(
        height = 20.dp,
        horizontalSpacing = Spacing6,
        iconGap = 2.dp,
        shape = SDGCornerRadius.BoxRadius.Radius6,
        typography = SDGTypography.Body3R,
    )
}