package com.shopl.sdg.component.icon_label.extension

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.shopl.sdg.component.icon_label.resolve
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * AnnotatedString을 사용하는 Icon Label입니다.
 *
 * 좌우 아이콘을 모두 노출하면 [labelOverflow] 값과 관계없이 Truncate(1줄 말줄임)로 표시합니다.
 * 아이콘 tint가 없으면 [labelColor]를 따릅니다.
 *
 * @param label 표시할 Label 텍스트
 * @param labelColor Label 색상
 * @param size Icon Label 크기
 * @param fontWeight Label 굵기
 * @param gap 아이콘과 Label 사이 간격
 * @param modifier Modifier
 * @param leftIc 좌측 아이콘
 * @param rightIc 우측 아이콘
 * @param labelOverflow Label이 길어지는 경우 노출 방식(Text Mode)
 * @param isFillMaxWidth 가용 너비를 모두 채울지 여부
 * @param onClick 클릭 이벤트
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
    val appliedOverflow = labelOverflow.resolve(
        hasLeftIc = leftIc != null,
        hasRightIc = rightIc != null,
    )

    Row(
        modifier = modifier
            .then(
                if (isFillMaxWidth) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier
                },
            )
            .width(IntrinsicSize.Max)
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
                color = leftIc.tint ?: labelColor,
            )
        }

        SDGText(
            modifier = Modifier.weight(weight = 1f, fill = isFillMaxWidth),
            text = label,
            textColor = labelColor,
            typography = fontWeight.typography(size),
            maxLines = appliedOverflow.maxLines,
            overflow = appliedOverflow.textOverflow,
        )

        if (rightIc != null) {
            SDGImage(
                modifier = Modifier
                    .padding(vertical = size.iconVerticalPadding)
                    .size(SDGIconLabelIconSize),
                resId = rightIc.resId,
                color = rightIc.tint ?: labelColor,
            )
        }
    }
}

private val SDGIconLabelIconSize = 14.dp
