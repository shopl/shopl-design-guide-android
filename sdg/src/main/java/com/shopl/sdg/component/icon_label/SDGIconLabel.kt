package com.shopl.sdg.component.icon_label

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Icon Label
 *
 * 아이콘과 텍스트를 조합하여 보여주는 컴포넌트
 *
 * @version 2.1.29
 *
 * @param labelOverflow Label이 길어지는 경우 노출 방식
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=10507-19135&m=dev">Figma</a>
 */
@Composable
fun SDGIconLabel(
    label: String,
    labelColor: Color,
    size: SDGIconLabelSize,
    fontWeight: SDGIconLabelFontWeight,
    gap: SDGIconLabelGap,
    onClick: (() -> Unit)?,
    labelOverflow: SDGIconLabelOverflow,
    modifier: Modifier = Modifier,
    leftIc: SDGIconLabelIcon? = null,
    rightIc: SDGIconLabelIcon? = null,
    isFillMaxWidth: Boolean = false,
) {
    Row(
        modifier = modifier
            .then(
                if (isFillMaxWidth) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier
                },
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                },
            ),
        horizontalArrangement = Arrangement.spacedBy(gap.value),
        verticalAlignment = Alignment.Top,
    ) {
        if (leftIc != null) {
            SDGImage(
                modifier = Modifier
                    .padding(vertical = size.iconVerticalPadding)
                    .size(SDGIconLabelIconSize),
                resId = leftIc.resId,
                color = leftIc.tint,
            )
        }

        SDGText(
            modifier = if (isFillMaxWidth) {
                Modifier.weight(1f)
            } else {
                Modifier
            },
            text = label,
            textColor = labelColor,
            typography = fontWeight.typography(size),
            maxLines = labelOverflow.maxLines,
            overflow = labelOverflow.textOverflow,
        )

        if (rightIc != null) {
            SDGImage(
                modifier = Modifier
                    .padding(vertical = size.iconVerticalPadding)
                    .size(SDGIconLabelIconSize),
                resId = rightIc.resId,
                color = rightIc.tint,
            )
        }
    }
}

/**
 * 신규 Icon Label API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "text/type/spacing/maxLines 대신 label/fontWeight/gap/labelOverflow를 사용하세요.",
)
@Composable
@Suppress("DEPRECATION")
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
    val labelOverflow = SDGIconLabelOverflow.fromMaxLines(maxLines)
    val onClick = onClickLeftIcon ?: onClickRightIcon

    Row(
        modifier = modifier
            .then(
                if (isFillMaxWidth) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier
                },
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable(hasRipple = false) { onClick() }
                } else {
                    Modifier
                },
            ),
        horizontalArrangement = Arrangement.spacedBy(spacing.gap.value),
        verticalAlignment = Alignment.Top,
    ) {
        if (leftIconResId != null) {
            SDGImage(
                modifier = Modifier
                    .padding(vertical = size.iconVerticalPadding)
                    .size(SDGIconLabelIconSize),
                resId = leftIconResId,
                color = leftIconTint,
            )
        }

        SDGText(
            modifier = if (isFillMaxWidth) {
                Modifier.weight(1f)
            } else {
                Modifier
            },
            text = text,
            textColor = textColor,
            typography = type.fontWeight.typography(size),
            maxLines = labelOverflow.maxLines,
            overflow = labelOverflow.textOverflow,
        )

        if (rightIconResId != null) {
            SDGImage(
                modifier = Modifier
                    .padding(vertical = size.iconVerticalPadding)
                    .size(SDGIconLabelIconSize),
                resId = rightIconResId,
                color = rightIconTint,
            )
        }
    }
}

private val SDGIconLabelIconSize = 14.dp

@Preview(showBackground = true)
@Composable
private fun PreviewSDGIconLabel(
    @PreviewParameter(SDGIconLabelPreviewParameterProvider::class)
    param: SDGIconLabelPreviewParam,
) {
    with(param) {
        SDGIconLabel(
            label = label,
            labelColor = labelColor,
            size = size,
            fontWeight = fontWeight,
            gap = gap,
            leftIc = leftIc,
            rightIc = rightIc,
            labelOverflow = labelOverflow,
            isFillMaxWidth = isFillMaxWidth,
            onClick = null,
        )
    }
}
