@file:Suppress("DEPRECATION")

package com.shopl.sdg.component.badge.box.extension

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.badge.box.SDGBoxBadge
import com.shopl.sdg.component.badge.box.SDGBoxBadgeIcon
import com.shopl.sdg.component.badge.box.SDGBoxBadgeSize
import com.shopl.sdg.component.badge.box.SDGBoxBadgeType
import com.shopl.sdg.component.badge.box.toFontWeight
import com.shopl.sdg.component.badge.box.toStyle
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

/**
 * 신규 Box Badge API와의 하위 호환성을 위한 레거시 [RowScope] API입니다.
 */
@Deprecated(
    message = "RowScope 확장 대신 신규 SDGBoxBadge API를 사용하세요.",
)
@Composable
fun RowScope.SDGBoxBadge(
    weight: Float,
    size: SDGBoxBadgeSize,
    type: SDGBoxBadgeType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    enable: Boolean = true,
    fill: Boolean = false,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .weight(weight = weight, fill = fill)
            .padding(marginValues),
    ) {
        SDGBoxBadge(
            label = label,
            style = type.toStyle(),
            fontWeight = size.toFontWeight(),
            labelColor = labelColor,
            backgroundColor = backgroundColor,
            isFillMaxWidth = fill,
            leftIc = if (leftIcon != null && leftIconTint != null) {
                SDGBoxBadgeIcon(
                    resId = leftIcon,
                    tint = leftIconTint,
                )
            } else {
                null
            },
            rightIc = if (rightIcon != null && rightIconTint != null) {
                SDGBoxBadgeIcon(
                    resId = rightIcon,
                    tint = rightIconTint,
                )
            } else {
                null
            },
            onClick = onClick.takeIf { enable },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGBoxBadge() {
    Row(horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8)) {
        SDGBoxBadge(
            weight = 1f,
            size = SDGBoxBadgeSize.XSmall,
            type = SDGBoxBadgeType.Solid,
            label = "Label 1",
            labelColor = SDGColor.Neutral900,
            backgroundColor = SDGColor.Neutral0,
        )

        SDGBoxBadge(
            weight = 1f,
            size = SDGBoxBadgeSize.XSmall,
            type = SDGBoxBadgeType.Solid,
            label = "Label 2",
            labelColor = SDGColor.Neutral900,
            backgroundColor = SDGColor.Neutral0,
        )
    }
}
