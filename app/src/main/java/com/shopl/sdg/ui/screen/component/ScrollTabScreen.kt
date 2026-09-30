package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.tab.scroll.SDGScrollTab
import com.shopl.sdg.component.tab.scroll.SDGScrollTabSize
import com.shopl.sdg.component.tab.scroll.SDGScrollTabStyle
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Scroll Tab
 *
 * 두 크기와 두 스타일의 선택 상태, 다수 탭의 수평 스크롤 및 긴 라벨의 말줄임을 확인합니다.
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8213-5076&m=dev">Figma</a>
 */
@Composable
internal fun ScrollTabScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = ComponentScene.Tab.ScrollTab.displayLabel,
        description = "수평 스크롤을 통해 다수의 카테고리를 탐색하고, 라벨과 인디케이터로 선택 상태를 전달하는 탭 컴포넌트",
        bodyContent = { ScrollTabScreenContent() },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

/** 크기·스타일별 예시와 여백, 말줄임 및 5개를 초과한 탭 예시를 나열합니다. */
@Composable
private fun ScrollTabScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SDGSpacing.Spacing16, vertical = SDGSpacing.Spacing24),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing24),
    ) {
        SDGScrollTabSize.entries.forEach { size ->
            ScrollTabSection(title = "$size / With Underline", size = size)
            ScrollTabSection(title = "$size / Only Text", size = size, style = SDGScrollTabStyle.OnlyText)
        }
        ScrollTabSection(
            title = "수평 스크롤 / 5개 초과",
            titles = persistentListOf("전체", "진행중", "완료", "보류", "취소", "예정", "검토중", "승인됨", "거절됨", "삭제됨"),
            contentPadding = PaddingValues(horizontal = SDGSpacing.Spacing16),
        )
        ScrollTabSection(
            title = "긴 라벨 / 말줄임",
            titles = persistentListOf("짧은 라벨", "매우 긴 라벨입니다. 말줄임표가 보여야 합니다.", "중간 라벨"),
            maxItemWidth = 100.dp,
        )
        ScrollTabSection(
            title = "Baseline Divider / 내부 여백",
            titles = persistentListOf("Label", "Label"),
            contentPadding = PaddingValues(SDGSpacing.Spacing20),
        )
    }
}

/** 각 예시는 첫 번째 탭에서 시작하며 클릭 시 라벨을 유지한 채 하나의 탭만 선택합니다. */
@Composable
private fun ScrollTabSection(
    title: String,
    size: SDGScrollTabSize = SDGScrollTabSize.Large,
    style: SDGScrollTabStyle = SDGScrollTabStyle.WithUnderline,
    titles: PersistentList<String> = persistentListOf("Label", "Label", "Label", "Label", "Label"),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    maxItemWidth: Dp? = null,
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8),
    ) {
        SDGText(
            text = title,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB,
        )
        SDGScrollTab(
            style = style,
            size = size,
            titles = titles,
            selectedTab = selectedTab,
            onTabClick = { selectedTab = it },
            contentPadding = contentPadding,
            maxItemWidth = maxItemWidth,
        )
    }
}

/** 샘플 화면에서 크기·스타일별 탭을 확인하는 미리보기입니다. */
@Preview
@Composable
private fun PreviewScrollTabScreen() {
    ShoplDesignGuideTheme {
        ScrollTabScreen(onClickBack = {}, onClickMenu = {})
    }
}
