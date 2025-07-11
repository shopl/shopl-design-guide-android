package com.shopl.sdg.component.icon_text

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_common.util.textCenterAlignment
import com.shopl.sdg_resource.R

/**
 * SDG - Icon Text
 *
 * @param size [SDGIconTextSize] 내부 텍스트 Size, LineHeight 조절
 * @param type [SDGIconTextType] 내부 텍스트 Weight 조절
 * @param spacing [SDGIconTextSpacing] 아이콘과 텍스트 사이 간격
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=10507-19136&m=dev">Figma</a>
 */
@Composable
fun SDGIconText(
    text: String,
    textColor: Color,
    size: SDGIconTextSize,
    type: SDGIconTextType,
    spacing: SDGIconTextSpacing,
    modifier: Modifier = Modifier,
    @DrawableRes leftIconResId: Int? = null,
    leftIconTint: Color? = null,
    onClickLeftIcon: (() -> Unit)? = null,
    @DrawableRes rightIconResId: Int? = null,
    rightIconTint: Color? = null,
    onClickRightIcon: (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    isFillMaxWidth: Boolean = true,
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val alignment = remember(textLayoutResult) {
        textCenterAlignment(textLayoutResult)
    }

    val typography = getIconListItemTypography(type = type, size = size)

    Row(
        modifier = modifier.then(
            if (isFillMaxWidth) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
            }
        ),
        horizontalArrangement = Arrangement.spacedBy(spacing.value)
    ) {
        if (leftIconResId != null && leftIconTint != null) {
            SDGImage(
                modifier = Modifier
                    .align(alignment)
                    .size(14.dp)
                    .then(
                        if (onClickLeftIcon != null) {
                            Modifier.clickable { onClickLeftIcon() }
                        } else {
                            Modifier
                        }
                    ),
                resId = leftIconResId,
                color = leftIconTint
            )
        }
        SDGText(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f, isFillMaxWidth),
            text = text,
            textColor = textColor,
            typography = typography,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult = it }
        )
        if (rightIconResId != null && rightIconTint != null) {
            SDGImage(
                modifier = Modifier
                    .align(alignment)
                    .size(14.dp)
                    .then(
                        if (onClickRightIcon != null) {
                            Modifier.clickable { onClickRightIcon() }
                        } else {
                            Modifier
                        }
                    ),
                resId = rightIconResId,
                color = rightIconTint
            )
        }
    }
}

/**
 * [SDGIconTextType]과 [SDGIconTextSize]에 맞는 Typography 반환
 */
private fun getIconListItemTypography(
    type: SDGIconTextType,
    size: SDGIconTextSize
): SDGTypography {
    return when (type) {
        SDGIconTextType.Basic -> when (size) {
            SDGIconTextSize.Size12 -> SDGTypography.Body3R
            SDGIconTextSize.Size14 -> SDGTypography.Body2R
            SDGIconTextSize.Size16 -> SDGTypography.Body1R
        }

        SDGIconTextType.Empha -> when (size) {
            SDGIconTextSize.Size12 -> SDGTypography.Body3SB
            SDGIconTextSize.Size14 -> SDGTypography.Body2SB
            SDGIconTextSize.Size16 -> SDGTypography.Body1SB
        }
    }
}


@Preview
@Composable
private fun PreviewSDGIconText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SDGColor.Neutral0)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SDGIconText(
            text = "왼쪽 아이콘",
            textColor = SDGColor.Neutral500,
            size = SDGIconTextSize.Size14,
            type = SDGIconTextType.Basic,
            spacing = SDGIconTextSpacing.Spacing4,
            leftIconResId = R.drawable.ic_common_warning,
            leftIconTint = SDGColor.Red300
        )
        SDGIconText(
            text = "오른쪽 아이콘",
            textColor = SDGColor.Neutral500,
            size = SDGIconTextSize.Size16,
            type = SDGIconTextType.Empha,
            spacing = SDGIconTextSpacing.Spacing2,
            rightIconResId = R.drawable.ic_common_warning,
            rightIconTint = SDGColor.Red300
        )
        SDGIconText(
            text = "아이콘 없음",
            textColor = SDGColor.Neutral500,
            size = SDGIconTextSize.Size12,
            type = SDGIconTextType.Basic,
            spacing = SDGIconTextSpacing.Spacing4
        )
    }
}