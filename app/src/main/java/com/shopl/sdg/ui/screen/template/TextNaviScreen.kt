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
import com.shopl.sdg.component.navigation.text.SDGTextNaviBackButton
import com.shopl.sdg.component.navigation.text.SDGTextNaviLabel
import com.shopl.sdg.component.navigation.text.SDGTextNaviSubItem
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
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19821-30436&m=dev">Figma</a>
 */
@Composable
internal fun TextNaviScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    var actionClickCount by remember { mutableIntStateOf(0) }

    SDGSampleBaseScaffold(
        name = TemplateScene.Navigation.TextNavi.displayLabel,
        description = "화면의 상단에 위치하여 현재 위치를 알려주는 타이틀과 우측에 1개 기능을 텍스트로 조합한 컴포넌트",
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
                    "모든 구성 요소는 수평으로 중앙 정렬합니다.",
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
    val backButton = SDGTextNaviBackButton.True(onClick = onClickBack)
    val subIcon = SDGTextNaviSubItem.Icon(
        iconResId = R.drawable.ic_common_plus,
        onClick = onClickAction,
        iconColor = null,
        showDot = false,
    )
    val subLabel = SDGTextNaviSubItem.Label(text = "추가", onClick = onClickAction)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SDGSpacing.Spacing40),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing20),
    ) {
        SDGText(
            text = "보조 항목 클릭: $actionClickCount",
            typography = SDGTypography.Body2R,
            textColor = SDGColor.Neutral700,
            modifier = Modifier.padding(horizontal = SDGSpacing.Spacing16),
        )
        SDGTextNavi(
            label = label,
            backButton = backButton,
            subIcon = subIcon,
            subLabel = subLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label.copy(text = "Dot 뱃지가 있는 보조 아이콘"),
            backButton = backButton,
            subIcon = subIcon.copy(iconResId = R.drawable.ic_navi_filter, showDot = true),
            subLabel = subLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label.copy(text = "뒤로가기 아이콘이 없는 경우"),
            backButton = SDGTextNaviBackButton.False,
            subIcon = subIcon,
            subLabel = subLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = SDGTextNaviLabel.False,
            backButton = backButton,
            subIcon = subIcon,
            subLabel = subLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label.copy(text = "텍스트만 있는 보조 항목"),
            backButton = backButton,
            subIcon = null,
            subLabel = subLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label.copy(text = "긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요"),
            backButton = backButton,
            subIcon = subIcon,
            subLabel = subLabel,
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label,
            backButton = backButton,
            subIcon = subIcon,
            subLabel = subLabel.copy(text = "변경사항 저장하기"),
            backgroundColor = SDGColor.Neutral100,
        )
        SDGTextNavi(
            label = label.copy(text = "파란 배경", textColor = SDGColor.Neutral0),
            backButton = backButton,
            subIcon = subIcon.copy(iconColor = SDGColor.Neutral0),
            subLabel = subLabel,
            backgroundColor = SDGColor.Primary300,
        )
        SDGTextNavi(
            label = label.copy(text = "분홍 배경"),
            backButton = backButton,
            subIcon = subIcon,
            subLabel = subLabel,
            backgroundColor = SDGColor.Red50,
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
