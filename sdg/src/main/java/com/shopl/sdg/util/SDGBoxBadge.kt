package com.shopl.sdg.util

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.badge.box.SDGBoxBadgeSize
import com.shopl.sdg.component.badge.box.SDGBoxBadgeType
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * [SDGBoxBadge]
 * 사용 편의를 위한 RowScope 정의
 */
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
    Row(
        modifier = Modifier
            .weight(weight, fill)
            .padding(marginValues)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(size.radius)
            )
            .then(
                if (type is SDGBoxBadgeType.Line) {
                    Modifier.border(
                        width = 1.dp,
                        color = type.lineColor,
                        shape = RoundedCornerShape(size = size.radius),
                    )
                } else {
                    Modifier
                }
            )
            .clip(RoundedCornerShape(size.radius))
            .then(
                if (enable) {
                    Modifier.clickable(
                        hasRipple = true,
                        rippleColor = SDGColor.Neutral900,
                        onClick = onClick,
                    )
                } else {
                    Modifier
                }
            )
            .padding(horizontal = size.horizontalPadding, vertical = size.verticalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (leftIcon != null && leftIconTint != null) {
            SDGImage(
                resId = leftIcon,
                color = leftIconTint,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(size.iconGap))
        }
        SDGText(
            text = label,
            textColor = labelColor,
            typography = size.typography,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (rightIcon != null && rightIconTint != null) {
            Spacer(modifier = Modifier.width(size.iconGap))
            SDGImage(
                resId = rightIcon,
                color = rightIconTint,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PrevSDGBoxBadge() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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