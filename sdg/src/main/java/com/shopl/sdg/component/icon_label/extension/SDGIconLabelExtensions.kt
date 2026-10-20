package com.shopl.sdg.component.icon_label.extension

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.icon_label.SDGIconLabelFontWeight
import com.shopl.sdg.component.icon_label.SDGIconLabelGap
import com.shopl.sdg.component.icon_label.SDGIconLabelIcon
import com.shopl.sdg.component.icon_label.SDGIconLabelOverflow
import com.shopl.sdg.component.icon_label.SDGIconLabelSize
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Icon Label
 *
 * 아이콘과 텍스트를 조합하여 보여주는 템플릿
 *
 * @param marginValues 컴포넌트 외부 여백. 기존 Modifier API와 구분하기 위해 명시합니다.
 * @param isFillMaxWidth 양쪽 아이콘 사용 시에도 가용 너비를 채울지 여부
 * @version 2.1.29
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=10507-19135&m=dev">Figma</a>
 */
@Composable
fun SDGIconLabel(
    label: AnnotatedString,
    labelColor: Color,
    size: SDGIconLabelSize,
    fontWeight: SDGIconLabelFontWeight,
    gap: SDGIconLabelGap,
    marginValues: PaddingValues,
    leftIc: SDGIconLabelIcon? = null,
    rightIc: SDGIconLabelIcon? = null,
    labelOverflow: SDGIconLabelOverflow = SDGIconLabelOverflow.Full,
    isFillMaxWidth: Boolean = false,
) {
    SDGIconLabel(
        label = label,
        labelColor = labelColor,
        size = size,
        fontWeight = fontWeight,
        gap = gap,
        modifier = Modifier.padding(marginValues),
        leftIc = leftIc,
        rightIc = rightIc,
        labelOverflow = labelOverflow,
        isFillMaxWidth = isFillMaxWidth,
        onClick = null,
    )
}

/**
 * AnnotatedString을 사용하는 Icon Label입니다.
 */
@Composable
fun SDGIconLabel(
    label: AnnotatedString,
    labelColor: Color,
    size: SDGIconLabelSize,
    fontWeight: SDGIconLabelFontWeight,
    gap: SDGIconLabelGap,
    modifier: Modifier = Modifier,
    leftIc: SDGIconLabelIcon? = null,
    rightIc: SDGIconLabelIcon? = null,
    labelOverflow: SDGIconLabelOverflow = SDGIconLabelOverflow.Full,
    isFillMaxWidth: Boolean = false,
    onClick: (() -> Unit)?,
) {
    require(leftIc != null || rightIc != null) { "Icon Label에는 하나 이상의 아이콘이 필요합니다." }

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
            modifier = Modifier.weight(
                weight = 1f,
                fill = isFillMaxWidth || leftIc == null || rightIc == null,
            ),
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

private val SDGIconLabelIconSize = 16.dp
