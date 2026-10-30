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
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
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

    data object SSmall : SDGBoxButtonSize(
        height = 28.dp,
        horizontalSpacing = Spacing8,
        iconGap = Spacing2,
        iconSize = 14.dp,
        shape = SDGCornerRadius.BoxRadius.Radius8,
        typography = SDGTypography.Body3R,
    ) {
        override val sizeName = "SSmall"
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

/**
 * Deprecated SDGBoxButton 래퍼 전용으로, 기존 버튼 크기별 기본 아이콘 크기를 신규 아이콘 크기로 변환합니다.
 * 신규 코드에서는 이 함수를 사용하지 않고 [SDGBoxButtonIconSize]를 직접 지정합니다.
 *
 * @param iconDownSize 기존 기본 크기를 2dp 줄일지 여부
 */
internal fun SDGBoxButtonSize.toLegacyIconSize(iconDownSize: Boolean): SDGBoxButtonIconSize =
    if (this == SDGBoxButtonSize.Medium) {
        if (iconDownSize) SDGBoxButtonIconSize.S else SDGBoxButtonIconSize.M
    } else {
        if (iconDownSize) SDGBoxButtonIconSize.XS else SDGBoxButtonIconSize.S
    }
