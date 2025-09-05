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
 * SDG - Icon Label
 *
 * @param size [SDGIconLabelSize] 내부 텍스트 Size, LineHeight 조절
 * @param type [SDGIconLabelType] 내부 텍스트 Weight 조절
 * @param spacing [SDGIconLabelSpacing] 아이콘과 텍스트 사이 간격
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=10507-19136&m=dev">Figma</a>
 */
@Composable
fun SDGIconLabel(
    text: String,
    textColor: Color,
    size: SDGIconLabelSize,
    type: SDGIconLabelType,
    spacing: SDGIconLabelSpacing,
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
        if (leftIconResId != null) {
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
        if (rightIconResId != null) {
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
 * [SDGIconLabelType]과 [SDGIconLabelSize]에 맞는 Typography 반환
 */
private fun getIconListItemTypography(
    type: SDGIconLabelType,
    size: SDGIconLabelSize
): SDGTypography {
    return when (type) {
        SDGIconLabelType.Basic -> when (size) {
            SDGIconLabelSize.Size12 -> SDGTypography.Body3R
            SDGIconLabelSize.Size14 -> SDGTypography.Body2R
            SDGIconLabelSize.Size16 -> SDGTypography.Body1R
        }

        SDGIconLabelType.Empha -> when (size) {
            SDGIconLabelSize.Size12 -> SDGTypography.Body3SB
            SDGIconLabelSize.Size14 -> SDGTypography.Body2SB
            SDGIconLabelSize.Size16 -> SDGTypography.Body1SB
        }
    }
}


@Preview
@Composable
private fun PreviewSDGIconLabel() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SDGColor.Neutral0)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SDGIconLabel(
            text = "왼쪽 아이콘",
            textColor = SDGColor.Neutral500,
            size = SDGIconLabelSize.Size14,
            type = SDGIconLabelType.Basic,
            spacing = SDGIconLabelSpacing.Spacing4,
            leftIconResId = R.drawable.ic_common_warning,
            leftIconTint = SDGColor.Red300
        )
        SDGIconLabel(
            text = "오른쪽 아이콘",
            textColor = SDGColor.Neutral500,
            size = SDGIconLabelSize.Size16,
            type = SDGIconLabelType.Empha,
            spacing = SDGIconLabelSpacing.Spacing2,
            rightIconResId = R.drawable.ic_common_warning,
            rightIconTint = null
        )
        SDGIconLabel(
            text = "아이콘 없음",
            textColor = SDGColor.Neutral500,
            size = SDGIconLabelSize.Size12,
            type = SDGIconLabelType.Basic,
            spacing = SDGIconLabelSpacing.Spacing4
        )
    }
}