package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.progress.linear.SDGLinearProgress
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

private const val HALF_PROGRESS = 0.5f

@Composable
internal fun LinearProgressScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = ComponentScene.Progress.LinearProgress.displayLabel,
        description = "UI를 불러오거나 사용자가 취한 액션을 처리할 경우 사용하며, 그 처리 과정이 진행 중임을 알려주는 문구가 포함된 바 형태의 컴포넌트",
        bodyContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SDGSpacing.Spacing24),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing24),
            ) {
                SDGText(
                    text = "Determinate",
                    textColor = SDGColor.Neutral700,
                    typography = SDGTypography.Body1SB,
                )
                SDGLinearProgress(progress = 0f, label = "업로드 준비 중입니다 (0%)")
                SDGLinearProgress(progress = HALF_PROGRESS, label = "업로드 중입니다 (50%)")
                SDGLinearProgress(progress = 1f, label = "업로드가 완료됐습니다 (100%)")

                SDGText(
                    text = "Indeterminate",
                    textColor = SDGColor.Neutral700,
                    typography = SDGTypography.Body1SB,
                )
                SDGLinearProgress(label = "업로드 중입니다")
                SDGLinearProgress()
                SDGLinearProgress(
                    label = "파일을 업로드하고 있습니다. 잠시만 기다려 주세요.",
                    labelColor = SDGColor.Primary400,
                )
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewLinearProgressScreen() {
    ShoplDesignGuideTheme {
        LinearProgressScreen(onClickBack = {}, onClickMenu = {})
    }
}
