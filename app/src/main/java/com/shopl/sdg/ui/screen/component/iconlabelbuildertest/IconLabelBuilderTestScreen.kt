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
                labelOverflow = SDGIconLabelBuilderTestOverflow.SingleLineEllipsis,
                isFillMaxWidth = true,
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
                isFillMaxWidth = true,
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
        // TODO(resource-owner): "Label" 전용 문자열 키가 필요합니다. 등록 후 샘플 label에 연결합니다.
        IconLabelBuilderTestScreen(label = "")
    }
}
