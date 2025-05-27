package com.shopl.sdg.component.badge.box

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Badge - Box Badge
 * 특정 상태나 데이터 구분 또는 전달을 위한 컴포넌트
 */
@Composable
fun SDGBoxBadge(
    size: SDGBoxBadgeSize,
    type: SDGBoxBadgeType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    isFillMaxWidth: Boolean = false,
    enable: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(marginValues)
            .then(
                if (isFillMaxWidth) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier
                }
            )
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
    SDGBoxBadge(
        size = SDGBoxBadgeSize.XSmall,
        type = SDGBoxBadgeType.Solid,
        label = "Label",
        labelColor = SDGColor.Neutral900,
        backgroundColor = SDGColor.Neutral0,
    )
}