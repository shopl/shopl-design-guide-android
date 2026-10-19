package com.shopl.sdg.component.icon_label_builder_test.extension

import androidx.compose.foundation.layout.Arrangement
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
 */
@Composable
fun SDGIconLabelBuilderTest(
    label: AnnotatedString,
    labelColor: Color,
    size: SDGIconLabelBuilderTestSize,
    fontWeight: SDGIconLabelBuilderTestFontWeight,
    gap: SDGIconLabelBuilderTestGap,
    modifier: Modifier = Modifier,
    leftIc: SDGIconLabelBuilderTestIcon? = null,
    rightIc: SDGIconLabelBuilderTestIcon? = null,
    labelOverflow: SDGIconLabelBuilderTestOverflow = SDGIconLabelBuilderTestOverflow.Full,
    isFillMaxWidth: Boolean = false,
    onClick: (() -> Unit)?,
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
                .align(Alignment.CenterVertically)
                .then(
                    if (isFillMaxWidth) {
                        Modifier.weight(1f)
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

private val SDGIconLabelBuilderTestIconSize = 14.dp
