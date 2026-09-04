package com.shopl.sdg.component.badge.box

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.badge.box.preview.SDGBoxBadgePreviewParam
import com.shopl.sdg.component.badge.box.preview.SDGBoxBadgePreviewParameterProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

private val SDGBoxBadgeHeight = 20.dp
private val SDGBoxBadgeIconSize = 14.dp
private val SDGBoxBadgeBorderWidth = 1.dp

/**
 * SDG - Badge - Box Badge
 *
 * 특정 상태나 데이터 구분 또는 전달을 위한 컴포넌트
 *
 * @version 2.1.27
 *
 * @param isFillMaxWidth 부모가 허용하는 최대 너비를 채울지 여부
 * @param marginValues 컴포넌트 외부 여백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18890-9265&m=dev">Figma</a>
 */
@Composable
fun SDGBoxBadge(
    label: String,
    style: SDGBoxBadgeStyle,
    fontWeight: SDGBoxBadgeFontWeight,
    labelColor: Color,
    backgroundColor: Color,
    leftIc: SDGBoxBadgeIcon? = null,
    rightIc: SDGBoxBadgeIcon? = null,
    marginValues: PaddingValues = PaddingValues(),
    isFillMaxWidth: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .padding(marginValues)
            .then(if (isFillMaxWidth) Modifier.fillMaxWidth() else Modifier)
            .height(SDGBoxBadgeHeight)
            .clip(SDGCornerRadius.BoxRadius.Radius6)
            .background(color = backgroundColor)
            .then(
                if (style is SDGBoxBadgeStyle.Line) {
                    Modifier.border(
                        width = SDGBoxBadgeBorderWidth,
                        color = style.lineColor,
                        shape = SDGCornerRadius.BoxRadius.Radius6,
                    )
                } else {
                    Modifier
                },
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                },
            )
            .padding(horizontal = Spacing6),
        horizontalArrangement = spacedBy(space = Spacing4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leftIc?.let { icon ->
            SDGImage(
                modifier = Modifier.size(SDGBoxBadgeIconSize),
                resId = icon.resId,
                color = icon.tint,
            )
        }

        SDGText(
            modifier = Modifier.weight(weight = 1f, fill = false),
            text = label,
            textColor = labelColor,
            typography = fontWeight.typography,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        rightIc?.let { icon ->
            SDGImage(
                modifier = Modifier.size(SDGBoxBadgeIconSize),
                resId = icon.resId,
                color = icon.tint,
            )
        }
    }
}

/**
 * 신규 Box Badge API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "size/type 대신 style/fontWeight를 사용하는 신규 SDGBoxBadge API를 사용하세요.",
)
@Composable
@Suppress("DEPRECATION")
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
    SDGBoxBadge(
        label = label,
        style = type.toStyle(),
        fontWeight = size.toFontWeight(),
        labelColor = labelColor,
        backgroundColor = backgroundColor,
        isFillMaxWidth = isFillMaxWidth,
        marginValues = marginValues,
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

@Preview(showBackground = true)
@Composable
private fun PreviewSDGBoxBadge(
    @PreviewParameter(SDGBoxBadgePreviewParameterProvider::class)
    param: SDGBoxBadgePreviewParam,
) {
    with(param) {
        SDGBoxBadge(
            label = label,
            style = style,
            fontWeight = fontWeight,
            labelColor = labelColor,
            backgroundColor = backgroundColor,
            isFillMaxWidth = isFillMaxWidth,
            leftIc = leftIc,
            rightIc = rightIc,
        )
    }
}
