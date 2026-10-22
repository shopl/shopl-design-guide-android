package com.shopl.sdg.ui.screen.component.iconlabelbuildertest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTest
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTestFontWeight
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTestGap
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTestIcon
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTestOverflow
import com.shopl.sdg.component.icon_label_builder_test.SDGIconLabelBuilderTestSize
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

@Composable
internal fun IconLabelBuilderTestScreen(label: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SDGSpacing.Spacing16,
                vertical = SDGSpacing.Spacing24,
            ),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8),
    ) {
        SDGText(
            text = "Icon Label Builder Test",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB,
        )

        for (size in SDGIconLabelBuilderTestSize.entries) {
            SDGIconLabelBuilderTest(
                label = label,
                labelColor = SDGColor.Neutral700,
                size = size,
                fontWeight = SDGIconLabelBuilderTestFontWeight.Normal,
                gap = SDGIconLabelBuilderTestGap.Spacing4,
                leftIc = SDGIconLabelBuilderTestIcon(
                    resId = R.drawable.ic_common_help,
                    tint = SDGColor.Primary400,
                ),
                rightIc = SDGIconLabelBuilderTestIcon(
                    resId = R.drawable.ic_common_next_s,
                    tint = SDGColor.Neutral700,
                ),
                isFillMaxWidth = true,
                labelOverflow = SDGIconLabelBuilderTestOverflow.SingleLineEllipsis,
                onClick = null,
            )

            SDGIconLabelBuilderTest(
                label = label,
                labelColor = SDGColor.Neutral700,
                size = size,
                fontWeight = SDGIconLabelBuilderTestFontWeight.Bold,
                gap = SDGIconLabelBuilderTestGap.Spacing2,
                leftIc = SDGIconLabelBuilderTestIcon(
                    resId = R.drawable.ic_common_help,
                    tint = SDGColor.Primary400,
                ),
                labelOverflow = SDGIconLabelBuilderTestOverflow.SingleLineEllipsis,
                onClick = null,
            )

            SDGIconLabelBuilderTest(
                label = label,
                labelColor = SDGColor.Neutral700,
                size = size,
                fontWeight = SDGIconLabelBuilderTestFontWeight.Normal,
                gap = SDGIconLabelBuilderTestGap.Spacing4,
                rightIc = SDGIconLabelBuilderTestIcon(
                    resId = R.drawable.ic_common_next_s,
                    tint = SDGColor.Neutral700,
                ),
                labelOverflow = SDGIconLabelBuilderTestOverflow.Full,
                onClick = null,
            )
        }

        for (overflow in SDGIconLabelBuilderTestOverflow.entries) {
            SDGIconLabelBuilderTest(
                label = "아주 긴 텍스트가 들어가는 경우 전체 노출과 한 줄 말줄임을 비교할 수 있는 라벨입니다.",
                labelColor = SDGColor.Neutral700,
                size = SDGIconLabelBuilderTestSize.Small,
                fontWeight = SDGIconLabelBuilderTestFontWeight.Normal,
                gap = SDGIconLabelBuilderTestGap.Spacing4,
                leftIc = SDGIconLabelBuilderTestIcon(resId = R.drawable.ic_common_list),
                rightIc = SDGIconLabelBuilderTestIcon(resId = R.drawable.ic_common_next_s),
                labelOverflow = overflow,
                isFillMaxWidth = true,
                onClick = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewIconLabelBuilderTestScreen() {
    ShoplDesignGuideTheme {
        IconLabelBuilderTestScreen(label = "Label")
    }
}
