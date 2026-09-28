package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.badge.box.SDGBoxBadge
import com.shopl.sdg.component.badge.box.SDGBoxBadgeFontWeight
import com.shopl.sdg.component.badge.box.SDGBoxBadgeStyle
import com.shopl.sdg.scene.TemplateScene
import com.shopl.sdg.template.history.custom.SDGCustomHistory
import com.shopl.sdg.template.history.custom.SDGCustomHistoryPosition
import com.shopl.sdg.ui.base.SDGSampleBaseGuideLinesContent
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CustomHistoryScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = TemplateScene.CustomHistory.displayLabel,
        description = "Dot과 Line 타임라인을 기반으로 다양한 이력과 진행 흐름을 커스텀 구성하는 템플릿",
        bodyContent = { CustomHistoryContent() },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
        usageGuideLinesContent = {
            SDGSampleBaseGuideLinesContent(
                guideLineDescriptions = persistentListOf(
                    "History의 Width는 정해진 가용 범위 내에서 가변합니다.",
                ),
            )
        },
    )
}

@Composable
private fun CustomHistoryContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SDGSpacing.Spacing16,
                vertical = SDGSpacing.Spacing40,
            ),
    ) {
        SDGCustomHistory(
            position = SDGCustomHistoryPosition.TOP,
            dotColor = SDGColor.Primary300,
            header = {
                SampleHistoryHeader(
                    stepLabel = "1단계",
                )
            },
            body = { SampleHistoryBody("근무 일정: 09:00 - 18:00") },
        )

        SDGCustomHistory(
            position = SDGCustomHistoryPosition.MIDDLE,
            dotColor = SDGColor.Primary300,
            header = {
                SampleHistoryHeader(
                    stepLabel = "2단계",
                )
            },
            body = {
                SampleHistoryBody(
                    text = "출근 시간이 09:00에서 10:00으로 변경되었습니다.",
                    contentPadding = PaddingValues(
                        horizontal = SDGSpacing.Spacing8,
                        vertical = SDGSpacing.Spacing8,
                    ),
                )
            },
        )

        SDGCustomHistory(
            position = SDGCustomHistoryPosition.MIDDLE,
            dotColor = SDGColor.Primary300,
            header = {
                SampleHistoryHeader(
                    stepLabel = "3단계",
                )
            },
            body = {
                SampleHistoryBody("담당자: 홍길동\n처리 예정: 오늘 18:00")
            },
        )

        SDGCustomHistory(
            position = SDGCustomHistoryPosition.LAST,
            dotColor = SDGColor.Primary300,
            header = {
                SampleHistoryHeader(
                    stepLabel = "4단계",
                )
            },
            body = null,
        )
    }
}

@Composable
private fun SampleHistoryHeader(
    stepLabel: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = spacedBy(Spacing4),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f, true)
        ) {
            SDGImage(
                resId = R.drawable.ic_common_checkbold,
                color = SDGColor.Primary300,
                modifier = Modifier.size(18.dp),
            )
            SDGText(
                text = stepLabel,
                typography = SDGTypography.Body1SB,
                textColor = SDGColor.Neutral700
            )
            SDGBoxBadge(
                label = "승인",
                style = SDGBoxBadgeStyle.Solid,
                fontWeight = SDGBoxBadgeFontWeight.Normal,
                backgroundColor = SDGColor.Neutral150,
                labelColor = SDGColor.Neutral700,
                leftIc = null,
                rightIc = null
            )
        }
        SDGImage(
            resId = R.drawable.ic_common_next_s,
            color = SDGColor.Neutral600,
            modifier = Modifier
                .padding(13.dp)
                .size(14.dp),
        )
    }
}

@Composable
private fun SampleHistoryBody(
    text: String,
    contentPadding: PaddingValues = PaddingValues(SDGSpacing.Spacing12),
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SDGColor.Neutral150, SDGCornerRadius.BoxRadius.Radius8)
            .padding(contentPadding),
    ) {
        SDGText(
            text = "text",
            typography = SDGTypography.Body2R,
            textColor = SDGColor.Neutral700,
        )
    }
}

@Preview
@Composable
private fun PreviewCustomHistoryScreen() {
    ShoplDesignGuideTheme {
        CustomHistoryScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
