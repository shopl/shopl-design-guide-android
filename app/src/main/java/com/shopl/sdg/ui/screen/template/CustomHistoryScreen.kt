package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.scene.TemplateScene
import com.shopl.sdg.template.history.custom.SDGCustomHistory
import com.shopl.sdg.template.history.custom.SDGCustomHistoryPosition
import com.shopl.sdg.ui.base.SDGSampleBaseGuideLinesContent
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
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
                    title = "근무 일정이 등록되었습니다",
                    description = "2026. 09. 28 09:00",
                    modifier = Modifier
                        .background(SDGColor.PurpleP_a10, RoundedCornerShape(8.dp))
                        .padding(SDGSpacing.Spacing12),
                )
            },
            body = { SampleHistoryBody("근무 일정: 09:00 - 18:00") },
        )

        SDGCustomHistory(
            position = SDGCustomHistoryPosition.MIDDLE,
            dotColor = SDGColor.Primary300,
            header = {
                SampleHistoryHeader(
                    title = "근무 일정이 변경되었습니다",
                    description = "2026. 09. 28 09:30",
                    modifier = Modifier.padding(
                        horizontal = SDGSpacing.Spacing12,
                        vertical = SDGSpacing.Spacing8,
                    ),
                )
            },
            body = { SampleHistoryBody("출근 시간이 09:00에서 10:00으로 변경되었습니다.") },
            bodyContentPadding = PaddingValues(
                horizontal = SDGSpacing.Spacing8,
                vertical = SDGSpacing.Spacing8,
            ),
        )

        SDGCustomHistory(
            position = SDGCustomHistoryPosition.MIDDLE,
            dotColor = SDGColor.Primary300,
            header = {
                SampleHistoryHeader(
                    title = "담당자가 배정되었습니다",
                    description = "2026. 09. 28 10:00",
                    modifier = Modifier
                        .background(SDGColor.PurpleP_a10, RoundedCornerShape(8.dp))
                        .padding(SDGSpacing.Spacing12),
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
                    title = "검토가 완료되었습니다",
                    description = "2026. 09. 28 18:00",
                    modifier = Modifier.padding(
                        horizontal = SDGSpacing.Spacing12,
                        vertical = SDGSpacing.Spacing8,
                    ),
                )
            },
            body = null,
        )
    }
}

@Composable
private fun SampleHistoryHeader(
    title: String,
    description: String,
    modifier: Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalArrangement = spacedBy(SDGSpacing.Spacing4),
    ) {
        SDGText(
            text = title,
            typography = SDGTypography.Body1R,
            textColor = SDGColor.Neutral700,
        )
        SDGText(
            text = description,
            typography = SDGTypography.Body3R,
            textColor = SDGColor.Neutral500,
        )
    }
}

@Composable
private fun SampleHistoryBody(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SDGColor.Neutral150, RoundedCornerShape(8.dp))
            .padding(SDGSpacing.Spacing12),
    ) {
        SDGText(
            text = text,
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
