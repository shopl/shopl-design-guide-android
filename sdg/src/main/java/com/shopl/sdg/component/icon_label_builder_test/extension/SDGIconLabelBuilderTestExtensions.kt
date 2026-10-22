package com.shopl.sdg.component.icon_label_builder_test.extension

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
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTestFontWeight
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTestGap
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTestIcon
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTestOverflow
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTestSize
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * AnnotatedString을 사용하는 Icon Label입니다.
 * 라벨과 선택한 좌우 아이콘을 함께 표시하며 크기, 굵기, 간격과 넘침 처리를 지정합니다.
 *
 * @version 2.1.29
 *
 * @param label 표시할 주석 및 스타일 정보가 포함된 라벨 텍스트
 * @param labelColor 라벨 텍스트 색상
 * @param size 라벨 타이포그래피와 아이콘의 세로 여백을 결정하는 크기
 * @param fontWeight 크기에 따른 라벨 타이포그래피의 굵기
 * @param gap 아이콘과 라벨 사이의 간격
 * @param marginValues 컴포넌트 외부 여백
 * @param leftIc 왼쪽 아이콘의 리소스와 색상 정보. null이면 표시하지 않습니다.
 * @param rightIc 오른쪽 아이콘의 리소스와 색상 정보. null이면 표시하지 않습니다.
 * @param labelOverflow 라벨의 최대 줄 수와 넘침 처리 방식
 * @param isFillMaxWidth 전체 가용 너비를 채울지 여부. 한쪽 아이콘만 사용하면 항상 채웁니다.
 * @param onClick 컴포넌트 클릭 시 호출할 콜백. null이면 클릭 동작을 적용하지 않습니다.
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=10507-19135&m=dev">Figma</a>
 */
@Composable
fun SDGIconLabelBuilderTest(
    label: AnnotatedString,
    labelColor: Color,
    size: SDGIconLabelBuilderTestSize,
    fontWeight: SDGIconLabelBuilderTestFontWeight,
    gap: SDGIconLabelBuilderTestGap,
    marginValues: PaddingValues = PaddingValues(),
    leftIc: SDGIconLabelBuilderTestIcon? = null,
    rightIc: SDGIconLabelBuilderTestIcon? = null,
    labelOverflow: SDGIconLabelBuilderTestOverflow = SDGIconLabelBuilderTestOverflow.Full,
    isFillMaxWidth: Boolean = false,
    onClick: (() -> Unit)?,
) {
    val fillAvailableWidth = isFillMaxWidth || ((leftIc != null) xor (rightIc != null))

    Row(
        modifier = Modifier
            .padding(marginValues)
            .then(
                if (fillAvailableWidth) {
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
                    .size(SDGIconLabelBuilderTestIconSize),
                resId = leftIc.resId,
                color = leftIc.tint,
            )
        }

        SDGText(
            modifier = Modifier
                .then(
                    if (fillAvailableWidth) {
                        Modifier.weight(1f, true)
                    } else {
                        Modifier
                    },
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
                    .size(SDGIconLabelBuilderTestIconSize),
                resId = rightIc.resId,
                color = rightIc.tint,
            )
        }
    }
}

private val SDGIconLabelBuilderTestIconSize = 16.dp
