package com.shopl.sdg.component.icon_label.builder_test

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

private val IconSize = 16.dp

/**
 * SDG - Icon Label Builder Test
 *
 * 아이콘과 텍스트를 조합하여 보여주는 템플릿
 *
 * 한쪽 아이콘 구성은 부모의 가용 너비를 채우며, 양쪽 구성은 콘텐츠 너비를 사용합니다.
 * 아이콘 래퍼는 추출된 M/S/XS의 수직 패딩 3/2/1을 유지하고 라벨과 상단 정렬합니다.
 *
 * 제공된 Figma 선택 노드: 66:207 (파일 키 미제공)
 *
 * @version 2.1.29
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=10507-19135&m=dev">원본 Icon Label Figma</a>
 */
@Composable
fun SDGIconLabelBuilderTest(
    label: String,
    labelColor: Color,
    size: SDGIconLabelBuilderTestSize,
    fontWeight: SDGIconLabelBuilderTestFontWeight,
    gap: SDGIconLabelBuilderTestGap,
    labelOverflow: SDGIconLabelBuilderTestOverflow,
    leftIcon: SDGIconLabelBuilderTestIcon? = null,
    rightIcon: SDGIconLabelBuilderTestIcon? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    require(leftIcon != null || rightIcon != null) {
        "Icon Label Builder Test requires at least one icon."
    }
    val hasSingleIcon = leftIcon == null || rightIcon == null

    Row(
        modifier = Modifier
            .padding(marginValues)
            .then(if (hasSingleIcon) Modifier.fillMaxWidth() else Modifier),
        horizontalArrangement = Arrangement.spacedBy(gap.value),
        verticalAlignment = Alignment.Top,
    ) {
        if (leftIcon != null) {
            SDGImage(
                modifier = Modifier
                    .padding(vertical = size.iconVerticalPadding)
                    .size(IconSize),
                resId = leftIcon.iconResId,
                color = leftIcon.iconColor,
            )
        }
        SDGText(
            modifier = Modifier.weight(1f, fill = hasSingleIcon),
            text = label,
            textColor = labelColor,
            typography = when (fontWeight) {
                SDGIconLabelBuilderTestFontWeight.Normal -> size.normalTypography
                SDGIconLabelBuilderTestFontWeight.Semibold -> size.semiboldTypography
            },
            maxLines = labelOverflow.maxLines,
            overflow = labelOverflow.textOverflow,
        )
        if (rightIcon != null) {
            SDGImage(
                modifier = Modifier
                    .padding(vertical = size.iconVerticalPadding)
                    .size(IconSize),
                resId = rightIcon.iconResId,
                color = rightIcon.iconColor,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun PreviewSDGIconLabelBuilderTest(
    @PreviewParameter(SDGIconLabelBuilderTestPreviewParameterProvider::class)
    parameter: SDGIconLabelBuilderTestPreviewParameter,
) {
    with(parameter) {
        SDGIconLabelBuilderTest(
            label = label,
            labelColor = labelColor,
            size = size,
            fontWeight = fontWeight,
            gap = gap,
            labelOverflow = labelOverflow,
            leftIcon = leftIcon,
            rightIcon = rightIcon,
        )
    }
}
