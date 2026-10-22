package com.shopl.sdg.component.icon_label_builder_test

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Icon Label Builder Test
 *
 * 아이콘과 텍스트를 조합하여 보여주는 템플릿
 *
 * @version 2.1.29
 *
 * @param labelOverflow Label이 길어지는 경우 노출 방식
 * @param isFillMaxWidth 전체 가용 너비를 채울지 여부. 한쪽 아이콘만 사용하면 항상 채웁니다.
 * @param marginValues 컴포넌트 외부 여백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=10507-19135&m=dev">Figma</a>
 */
@Composable
fun SDGIconLabelBuilderTest(
    label: String,
    labelColor: Color,
    size: SDGIconLabelBuilderTestSize,
    fontWeight: SDGIconLabelBuilderTestFontWeight,
    gap: SDGIconLabelBuilderTestGap,
    onClick: (() -> Unit)?,
    labelOverflow: SDGIconLabelBuilderTestOverflow,
    marginValues: PaddingValues = PaddingValues(),
    leftIc: SDGIconLabelBuilderTestIcon? = null,
    rightIc: SDGIconLabelBuilderTestIcon? = null,
    isFillMaxWidth: Boolean = false,
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
                    .size(SDGIconLabelBuilderTestIconSize),
                resId = leftIc.resId,
                color = leftIc.tint,
            )
        }

        SDGText(
            // 아이콘을 먼저 측정해 긴 라벨에서도 오른쪽 아이콘의 공간을 확보합니다.
            modifier = Modifier.weight(1f, fill = fillAvailableWidth),
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

/**
 * 신규 Icon Label API와의 하위 호환성을 위한 레거시 API입니다.
 *
 * @param marginValues 컴포넌트 외부 여백
 */
@Deprecated(
    message = "text/type/spacing/maxLines 대신 label/fontWeight/gap/labelOverflow를 사용하세요.",
)
@Composable
@Suppress("DEPRECATION")
fun SDGIconLabelBuilderTest(
    text: String,
    textColor: Color,
    size: SDGIconLabelBuilderTestSize,
    type: SDGIconLabelBuilderTestType,
    spacing: SDGIconLabelBuilderTestSpacing,
    marginValues: PaddingValues = PaddingValues(),
    @DrawableRes leftIconResId: Int? = null,
    leftIconTint: Color? = null,
    onClickLeftIcon: (() -> Unit)? = null,
    @DrawableRes rightIconResId: Int? = null,
    rightIconTint: Color? = null,
    onClickRightIcon: (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    isFillMaxWidth: Boolean = true,
) {
    SDGIconLabelBuilderTest(
        label = text,
        labelColor = textColor,
        size = size,
        fontWeight = type.fontWeight,
        gap = spacing.gap,
        onClick = onClickLeftIcon ?: onClickRightIcon,
        labelOverflow = SDGIconLabelBuilderTestOverflow.fromMaxLines(maxLines),
        marginValues = marginValues,
        leftIc = leftIconResId?.let { resId ->
            SDGIconLabelBuilderTestIcon(
                resId = resId,
                tint = leftIconTint,
            )
        },
        rightIc = rightIconResId?.let { resId ->
            SDGIconLabelBuilderTestIcon(
                resId = resId,
                tint = rightIconTint,
            )
        },
        isFillMaxWidth = isFillMaxWidth,
    )
}

/**
 * 신규 Icon Label API와의 하위 호환성을 위한 레거시 API입니다.
 *
 * @param marginValues 컴포넌트 외부 여백
 */
@Deprecated(
    message = "text/type/spacing/maxLines 대신 label/fontWeight/gap/labelOverflow를 사용하세요.",
)
@Composable
@Suppress("DEPRECATION")
fun SDGIconLabelBuilderTest(
    text: AnnotatedString,
    textColor: Color,
    size: SDGIconLabelBuilderTestSize,
    type: SDGIconLabelBuilderTestType,
    spacing: SDGIconLabelBuilderTestSpacing,
    marginValues: PaddingValues = PaddingValues(),
    @DrawableRes leftIconResId: Int? = null,
    leftIconTint: Color? = null,
    onClickLeftIcon: (() -> Unit)? = null,
    @DrawableRes rightIconResId: Int? = null,
    rightIconTint: Color? = null,
    onClickRightIcon: (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    isFillMaxWidth: Boolean = true,
) {
    com.shopl.sdg.component.icon_label_builder_test.extension.SDGIconLabelBuilderTest(
        label = text,
        labelColor = textColor,
        size = size,
        fontWeight = type.fontWeight,
        gap = spacing.gap,
        onClick = onClickLeftIcon ?: onClickRightIcon,
        labelOverflow = SDGIconLabelBuilderTestOverflow.fromMaxLines(maxLines),
        marginValues = marginValues,
        leftIc = leftIconResId?.let { resId ->
            SDGIconLabelBuilderTestIcon(
                resId = resId,
                tint = leftIconTint,
            )
        },
        rightIc = rightIconResId?.let { resId ->
            SDGIconLabelBuilderTestIcon(
                resId = resId,
                tint = rightIconTint,
            )
        },
        isFillMaxWidth = isFillMaxWidth,
    )
}

private val SDGIconLabelBuilderTestIconSize = 16.dp

@Preview(showBackground = true)
@Composable
private fun PreviewSDGIconLabelBuilderTest(
    @PreviewParameter(SDGIconLabelBuilderTestPreviewParameterProvider::class)
    param: SDGIconLabelBuilderTestPreviewParameter,
) {
    with(param) {
        SDGIconLabelBuilderTest(
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
