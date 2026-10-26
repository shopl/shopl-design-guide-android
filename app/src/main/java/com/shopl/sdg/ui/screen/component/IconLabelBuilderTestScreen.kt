package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.icon_label.builder_test.SDGIconLabelBuilderTest
import com.shopl.sdg.component.icon_label.builder_test.SDGIconLabelBuilderTestFontWeight
import com.shopl.sdg.component.icon_label.builder_test.SDGIconLabelBuilderTestGap
import com.shopl.sdg.component.icon_label.builder_test.SDGIconLabelBuilderTestIcon
import com.shopl.sdg.component.icon_label.builder_test.SDGIconLabelBuilderTestOverflow
import com.shopl.sdg.component.icon_label.builder_test.SDGIconLabelBuilderTestSize
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG Sample App - Icon Label Builder Test
 *
 * @version 2.1.29
 */
@Composable
internal fun IconLabelBuilderTestScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = ComponentScene.IconLabelBuilderTest.displayLabel,
        description = "아이콘과 텍스트를 조합하여 보여주는 템플릿",
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
        bodyContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = SDGSpacing.Spacing16,
                        vertical = SDGSpacing.Spacing24,
                    ),
                verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing24),
            ) {
                for (size in SDGIconLabelBuilderTestSize.entries) {
                    for (fontWeight in SDGIconLabelBuilderTestFontWeight.entries) {
                        IconLabelBuilderTestSection(size = size, fontWeight = fontWeight)
                    }
                }
            }
        },
    )
}

@Composable
private fun IconLabelBuilderTestSection(
    size: SDGIconLabelBuilderTestSize,
    fontWeight: SDGIconLabelBuilderTestFontWeight,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8),
    ) {
        SDGText(
            text = "${size.name} / ${fontWeight.name}",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB,
        )
        for (gap in SDGIconLabelBuilderTestGap.entries) {
            for (overflow in SDGIconLabelBuilderTestOverflow.entries) {
                SDGText(
                    text = "${gap.name} / ${overflow.name}",
                    textColor = SDGColor.Neutral600,
                    typography = SDGTypography.Body3R,
                )
                for (placement in IconPlacement.entries) {
                    SDGIconLabelBuilderTest(
                        label = "${placement.name}: 아주 긴 텍스트가 들어가는 경우 전체 표시와 한 줄 말줄임을 확인합니다.",
                        labelColor = if (placement == IconPlacement.Right) {
                            SDGColor.Primary400
                        } else {
                            SDGColor.Neutral700
                        },
                        size = size,
                        fontWeight = fontWeight,
                        gap = gap,
                        labelOverflow = overflow,
                        leftIcon = if (placement != IconPlacement.Right) {
                            SDGIconLabelBuilderTestIcon(
                                iconResId = R.drawable.ic_common_help,
                                iconColor = SDGColor.Primary400,
                            )
                        } else {
                            null
                        },
                        rightIcon = if (placement != IconPlacement.Left) {
                            SDGIconLabelBuilderTestIcon(
                                iconResId = R.drawable.ic_common_next_s,
                                iconColor = SDGColor.Neutral700,
                            )
                        } else {
                            null
                        },
                    )
                }
                SDGIconLabelBuilderTest(
                    label = "Label",
                    labelColor = SDGColor.Neutral700,
                    size = size,
                    fontWeight = fontWeight,
                    gap = gap,
                    labelOverflow = overflow,
                    leftIcon = SDGIconLabelBuilderTestIcon(iconResId = R.drawable.ic_common_help),
                    rightIcon = SDGIconLabelBuilderTestIcon(iconResId = R.drawable.ic_common_next_s),
                )
            }
        }
    }
}

private enum class IconPlacement {
    Left,
    Right,
    Both,
}

@Preview(showBackground = true)
@Composable
private fun PreviewIconLabelBuilderTestScreen() {
    ShoplDesignGuideTheme {
        IconLabelBuilderTestScreen(onClickBack = {}, onClickMenu = {})
    }
}
