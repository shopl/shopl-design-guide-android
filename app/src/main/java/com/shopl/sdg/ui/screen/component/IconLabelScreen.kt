package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.icon_label.SDGIconLabel
import com.shopl.sdg.component.icon_label.SDGIconLabelFontWeight
import com.shopl.sdg.component.icon_label.SDGIconLabelGap
import com.shopl.sdg.component.icon_label.SDGIconLabelIcon
import com.shopl.sdg.component.icon_label.SDGIconLabelOverflow
import com.shopl.sdg.component.icon_label.SDGIconLabelSize
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseGuideLinesContent
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Icon Label
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=10507-19135&m=dev">Figma</a>
 */
@Composable
internal fun IconLabelScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = ComponentScene.IconLabel.displayLabel,
        description = "아이콘과 텍스트를 조합하여 보여주는 템플릿",
        bodyContent = {
            IconLabelScreenContent()
        },
        usageGuideLinesContent = {
            SDGSampleBaseGuideLinesContent(
                guideLineDescriptions = persistentListOf(
                    "아이콘을 둘 다 노출하지 않을 경우는 해당 컴포넌트로 가이드 하지 않습니다.",
                    "아이콘과 Label은 수평으로 상단 정렬합니다.",
                ),
            )
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun IconLabelScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SDGSpacing.Spacing16,
                vertical = SDGSpacing.Spacing24,
            ),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing24),
    ) {
        IconLabelSection(
            title = "Medium",
            size = SDGIconLabelSize.Medium,
        )

        IconLabelSection(
            title = "Small",
            size = SDGIconLabelSize.Small,
        )

        IconLabelSection(
            title = "XSmall",
            size = SDGIconLabelSize.XSmall,
        )

        IconLabelOverflowContent()
    }
}

@Composable
private fun IconLabelSection(
    title: String,
    size: SDGIconLabelSize,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8),
    ) {
        SDGText(
            text = title,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB,
        )

        SDGIconLabel(
            label = "Normal / Spacing4",
            labelColor = SDGColor.Neutral700,
            size = size,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = SDGIconLabelIcon(
                resId = R.drawable.ic_common_help,
                tint = SDGColor.Primary400,
            ),
            rightIc = SDGIconLabelIcon(
                resId = R.drawable.ic_common_next_s,
                tint = SDGColor.Neutral700,
            ),
            marginValues = PaddingValues(),
            labelOverflow = SDGIconLabelOverflow.SingleLineEllipsis,
        )

        SDGIconLabel(
            label = "Bold / Spacing2",
            labelColor = SDGColor.Neutral700,
            size = size,
            fontWeight = SDGIconLabelFontWeight.Bold,
            gap = SDGIconLabelGap.Spacing2,
            leftIc = SDGIconLabelIcon(
                resId = R.drawable.ic_common_help,
                tint = SDGColor.Primary400,
            ),
            marginValues = PaddingValues(),
            labelOverflow = SDGIconLabelOverflow.SingleLineEllipsis,
        )

        SDGIconLabel(
            label = "Right icon / Spacing4",
            labelColor = SDGColor.Secondary400,
            size = size,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing4,
            marginValues = PaddingValues(),
            rightIc = SDGIconLabelIcon(
                resId = R.drawable.ic_common_next_s,
                tint = SDGColor.Secondary400,
            ),
        )
    }
}

@Composable
private fun IconLabelOverflowContent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8),
    ) {
        SDGText(
            text = "Overflow",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB,
        )

        Box(modifier = Modifier.widthIn(max = 240.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8)) {
                SDGIconLabelOverflow.entries.forEach { overflow ->
                    SDGText(
                        text = overflow.name,
                        textColor = SDGColor.Neutral600,
                        typography = SDGTypography.Body3R,
                    )
                    SDGIconLabel(
                        label = "긴 라벨의 전체 노출과 한 줄 말줄임을 같은 너비에서 확인합니다.",
                        labelColor = SDGColor.Neutral700,
                        size = SDGIconLabelSize.Small,
                        fontWeight = SDGIconLabelFontWeight.Normal,
                        gap = SDGIconLabelGap.Spacing4,
                        marginValues = PaddingValues(),
                        leftIc = SDGIconLabelIcon(resId = R.drawable.ic_common_list),
                        rightIc = SDGIconLabelIcon(resId = R.drawable.ic_common_next_s),
                        labelOverflow = overflow,
                        isFillMaxWidth = true,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewIconLabelScreen() {
    ShoplDesignGuideTheme {
        IconLabelScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
