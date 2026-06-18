package com.shopl.sdg.component.button.box

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing10
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing3
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.typography.SDGTypography

@Stable
sealed class SDGBoxButtonSize(
    val height: Dp,
    val horizontalSpacing: Dp,
    val iconSize: Dp,
    val iconGap: Dp,
    val shape: Shape,
    val typography: SDGTypography,
) {
    abstract val sizeName: String

    data object Medium : SDGBoxButtonSize(
        height = 42.dp,
        horizontalSpacing = Spacing16,
        iconGap = Spacing4,
        iconSize = 16.dp,
        shape = SDGCornerRadius.BoxRadius.Radius12,
        typography = SDGTypography.Body2R,
    ) {
        override val sizeName = "Medium"
    }

    data object Small : SDGBoxButtonSize(
        height = 32.dp,
        horizontalSpacing = Spacing10,
        iconGap = Spacing3,
        iconSize = 14.dp,
        shape = SDGCornerRadius.BoxRadius.Radius8,
        typography = SDGTypography.Body2R,
    ) {
        override val sizeName = "Small"
    }

    data object XSmall : SDGBoxButtonSize(
        height = 20.dp,
        horizontalSpacing = Spacing6,
        iconGap = Spacing2,
        iconSize = 14.dp,
        shape = SDGCornerRadius.BoxRadius.Radius6,
        typography = SDGTypography.Body3R,
    ) {
        override val sizeName = "XSmall"
    }
}