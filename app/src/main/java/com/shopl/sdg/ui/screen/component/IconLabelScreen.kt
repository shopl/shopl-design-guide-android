package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

private const val LongLabel = "Label이 길어져 정해진 너비를 초과하는 경우의 표시 예시입니다. Label이 길어져 정해진 너비를 초과하는 경우의 표시 예시입니다."

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
        description = "아이콘과 텍스트를 조합하여 정보를 간결하게 전달하는 컴포넌트",
        bodyContent = {
            IconLabelScreenContent()
        },
        usageGuideLinesContent = {
            SDGSampleBaseGuideLinesContent(
                guideLineDescriptions = persistentListOf(
                    "아이콘을 둘 다 노출하지 않을 경우는 해당 컴포넌트로 가이드 하지 않습니다.",
                    "아이콘을 둘 다 노출할 경우는 Text Mode는 Truncate 타입으로 사용합니다.",
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
        IconLabelSizeSection(
            title = "Medium",
            size = SDGIconLabelSize.Medium,
        )

        IconLabelSizeSection(
            title = "Small",
            size = SDGIconLabelSize.Small,
        )

        IconLabelSizeSection(
            title = "XSmall",
            size = SDGIconLabelSize.XSmall,
        )

        IconLabelIconSection()

        IconLabelTextModeSection()
    }
}

@Composable
private fun IconLabelSizeSection(
    title: String,
    size: SDGIconLabelSize,
) {
    IconLabelSampleColumn(title = title) {
        SDGIconLabel(
            label = "Normal",
            labelColor = SDGColor.Neutral700,
            size = size,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = SDGIconLabelIcon(resId = R.drawable.ic_common_help),
            rightIc = SDGIconLabelIcon(resId = R.drawable.ic_common_next_s),
            labelOverflow = SDGIconLabelOverflow.SingleLineEllipsis,
            onClick = null,
        )

        SDGIconLabel(
            label = "Bold",
            labelColor = SDGColor.Neutral700,
            size = size,
            fontWeight = SDGIconLabelFontWeight.Bold,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = SDGIconLabelIcon(resId = R.drawable.ic_common_help),
            rightIc = SDGIconLabelIcon(resId = R.drawable.ic_common_next_s),
            labelOverflow = SDGIconLabelOverflow.SingleLineEllipsis,
            onClick = null,
        )
    }
}

@Composable
private fun IconLabelIconSection() {
    IconLabelSampleColumn(title = "Left ic & Right ic") {
        SDGIconLabel(
            label = "Left ic / Spacing4",
            labelColor = SDGColor.Neutral700,
            size = SDGIconLabelSize.Small,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = SDGIconLabelIcon(resId = R.drawable.ic_common_help),
            labelOverflow = SDGIconLabelOverflow.Full,
            isFillMaxWidth = true,
            onClick = null,
        )

        SDGIconLabel(
            label = "Right ic / Spacing2",
            labelColor = SDGColor.Neutral700,
            size = SDGIconLabelSize.Small,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing2,
            rightIc = SDGIconLabelIcon(resId = R.drawable.ic_common_next_s),
            labelOverflow = SDGIconLabelOverflow.Full,
            isFillMaxWidth = true,
            onClick = null,
        )

        SDGIconLabel(
            label = "Left ic + Right ic / Spacing4",
            labelColor = SDGColor.Primary400,
            size = SDGIconLabelSize.Small,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = SDGIconLabelIcon(resId = R.drawable.ic_common_help),
            rightIc = SDGIconLabelIcon(resId = R.drawable.ic_common_next_s),
            labelOverflow = SDGIconLabelOverflow.SingleLineEllipsis,
            onClick = null,
        )
    }
}

@Composable
private fun IconLabelTextModeSection() {
    IconLabelSampleColumn(title = "Text Mode") {
        SDGText(
            text = "Wrap (기본값) · Left ic",
            textColor = SDGColor.Neutral500,
            typography = SDGTypography.Body2R,
        )

        SDGIconLabel(
            label = LongLabel,
            labelColor = SDGColor.Neutral700,
            size = SDGIconLabelSize.Medium,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = SDGIconLabelIcon(resId = R.drawable.ic_common_help),
            labelOverflow = SDGIconLabelOverflow.Full,
            isFillMaxWidth = true,
            onClick = null,
        )

        SDGText(
            text = "Truncate · Left ic + Right ic",
            textColor = SDGColor.Neutral500,
            typography = SDGTypography.Body2R,
        )

        SDGIconLabel(
            label = LongLabel,
            labelColor = SDGColor.Neutral700,
            size = SDGIconLabelSize.Medium,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = SDGIconLabelIcon(resId = R.drawable.ic_common_help),
            rightIc = SDGIconLabelIcon(resId = R.drawable.ic_common_next_s),
            labelOverflow = SDGIconLabelOverflow.SingleLineEllipsis,
            isFillMaxWidth = true,
            onClick = null,
        )
    }
}

@Composable
private fun IconLabelSampleColumn(
    title: String,
    content: @Composable () -> Unit,
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

        content()
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
