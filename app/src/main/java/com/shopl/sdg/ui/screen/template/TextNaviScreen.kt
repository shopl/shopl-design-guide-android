package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.navigation.text.SDGTextNavi
import com.shopl.sdg.component.navigation.text.SDGTextNaviActionItem
import com.shopl.sdg.component.navigation.text.SDGTextNaviBackButton
import com.shopl.sdg.component.navigation.text.SDGTextNaviLabel
import com.shopl.sdg.component.navigation.text.ShowActionIcon
import com.shopl.sdg.scene.TemplateScene
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
 * SDG Sample App - Navigation - Text Navi
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=30016-9273&m=dev">Figma</a>
 */
@Composable
internal fun TextNaviScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    var actionClickCount by remember { mutableIntStateOf(0) }

    SDGSampleBaseScaffold(
        name = TemplateScene.Navigation.TextNavi.displayLabel,
        description = "화면 상단에서 현재 위치를 나타내는 타이틀과 화면 이동, 우측 주요 실행 기능을 제공하는 내비게이션 컴포넌트",
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
        bodyContent = {
            TextNaviContent(
                actionClickCount = actionClickCount,
                onClickBack = onClickBack,
                onClickAction = { actionClickCount++ },
            )
        },
        usageGuideLinesContent = {
            SDGSampleBaseGuideLinesContent(
                guideLineDescriptions = persistentListOf(
                    "Status Bar 영역을 제외한 모바일 화면의 최상단에 배치합니다.",
                    "모든 구성 요소는 세로 중앙 정렬합니다.",
                    "Action 아이콘과 라벨은 하나의 터치 영역으로 동작합니다.",
                    "Action 라벨은 완료, 저장, 편집 같은 짧은 동사형 문구로 작성합니다.",
                    "Action 라벨은 줄이지 않고 제목을 말줄임 처리합니다. 긴 제목과 긴 Action 라벨을 함께 쓰지 않습니다.",
                ),
            )
        },
    )
}

@Composable
private fun TextNaviContent(
    actionClickCount: Int,
    onClickBack: () -> Unit,
    onClickAction: () -> Unit,
) {
    val label = SDGTextNaviLabel.True(text = "화면 타이틀", textColor = SDGColor.Neutral900)
    val backButton = SDGTextNaviBackButton.True(onClick = onClickBack, iconColor = SDGColor.Neutral700)
    val showActionIcon = ShowActionIcon.True(
        iconResId = R.drawable.ic_common_plus,
        iconColor = null,
    )
    val actionLabel = SDGTextNaviActionItem.Label(text = "저장", textColor = SDGColor.Neutral700, onClick = onClickAction)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SDGSpacing.Spacing40),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing20),
    ) {
        SDGText(
            text = "Action 클릭: $actionClickCount",
            typography = SDGTypography.Body2R,
            textColor = SDGColor.Neutral700,
            modifier = Modifier.padding(horizontal = SDGSpacing.Spacing16),
        )
        SDGTextNavi(
            label = label,
            backButton = backButton,
            showActionIcon = showActionIcon,
            actionLabel = actionLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label.copy(text = "뒤로가기 아이콘이 없는 경우"),
            backButton = SDGTextNaviBackButton.False,
            showActionIcon = showActionIcon,
            actionLabel = actionLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = SDGTextNaviLabel.False,
            backButton = backButton,
            showActionIcon = showActionIcon,
            actionLabel = actionLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label.copy(text = "아이콘 없이 라벨만 있는 Action"),
            backButton = backButton,
            showActionIcon = ShowActionIcon.False,
            actionLabel = actionLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label.copy(text = "긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요"),
            backButton = backButton,
            showActionIcon = showActionIcon,
            actionLabel = actionLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label,
            backButton = backButton,
            showActionIcon = showActionIcon,
            actionLabel = actionLabel.copy(text = "변경사항 저장하기"),
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label.copy(text = "파란 배경", textColor = SDGColor.Neutral0),
            backButton = backButton.copy(iconColor = SDGColor.Neutral0),
            showActionIcon = showActionIcon.copy(iconColor = SDGColor.Neutral0),
            actionLabel = actionLabel.copy(textColor = SDGColor.Neutral0),
            backgroundColor = SDGColor.Primary300,
        )
        SDGTextNavi(
            label = label.copy(text = "분홍 배경"),
            backButton = backButton,
            showActionIcon = showActionIcon,
            actionLabel = actionLabel,
            backgroundColor = SDGColor.Red300_a10,
        )
        SDGTextNavi(
            label = label.copy(text = "투명 배경"),
            backButton = backButton,
            showActionIcon = showActionIcon,
            actionLabel = actionLabel,
            backgroundColor = SDGColor.Transparent,
        )
    }
}

@Preview
@Composable
private fun PreviewTextNaviScreen() {
    ShoplDesignGuideTheme {
        TextNaviScreen(onClickBack = {}, onClickMenu = {})
    }
}
