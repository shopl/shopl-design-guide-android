package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.navigation.category.SDGCategoryNavi
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviHasDropdown
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviIconItem
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviLeftIconItem
import com.shopl.sdg.scene.TemplateScene
import com.shopl.sdg.template.popup.list.anatomy.SDGListBottomSheet
import com.shopl.sdg.template.popup.list.anatomy.extension.SDGListPopupItemUiState
import com.shopl.sdg.ui.base.SDGSampleBaseGuideLinesContent
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoryNaviScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    var selectedTitle by remember { mutableStateOf("Label") }
    var showPopup by remember { mutableStateOf(false) }
    var showDot by remember { mutableStateOf(true) }
    val onDropdownClick = { showPopup = true }
    val backIcon = SDGCategoryNaviLeftIconItem(
        onClick = onClickBack,
        iconColor = null,
        contentDescription = "뒤로가기",
    )
    val rightIcons = persistentListOf(
        SDGCategoryNaviIconItem(
            iconResId = R.drawable.ic_navi_search,
            onClick = onDropdownClick,
            iconColor = null,
            showDot = false,
            contentDescription = "검색",
        ),
        SDGCategoryNaviIconItem(
            iconResId = R.drawable.ic_navi_filter,
            onClick = { showDot = !showDot },
            iconColor = null,
            showDot = showDot,
            contentDescription = "필터",
        ),
        SDGCategoryNaviIconItem(
            iconResId = R.drawable.ic_navi_option,
            onClick = onClickMenu,
            iconColor = null,
            showDot = false,
            contentDescription = "메뉴",
        ),
    )

    SDGSampleBaseScaffold(
        name = TemplateScene.Navigation.CategoryNavi.displayLabel,
        description = "화면의 상단에 위치하여 현재 위치를 알려주는 유형 변경이 가능한 타이틀과 고 사용자가 필요한 것을 찾도록 도와주는 아이콘으로 조합한 컴포넌트",
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
        bodyContent = {
            CategoryNaviContent(
                selectedTitle = selectedTitle,
                backIcon = backIcon,
                rightIcons = rightIcons,
                hasDropdown = SDGCategoryNaviHasDropdown.True(onClick = onDropdownClick),
            )
        },
        usageGuideLinesContent = {
            SDGSampleBaseGuideLinesContent(
                guideLineDescriptions = persistentListOf(
                    "Status Bar 영역을 제외한 모바일 화면의 최상단에 배치합니다.",
                    "모든 구성 요소는 수평으로 중앙 정렬합니다.",
                    "드롭다운을 선택하면 List Popup이 노출됩니다.",
                ),
            )
        },
    )

    if (showPopup) {
        SDGListBottomSheet(
            items = persistentListOf(
                "Label",
                "다른 카테고리",
                "긴 카테고리 이름이 들어가는 경우 말줄임표시 확인 필요",
            ).map {
                SDGListPopupItemUiState.create(title = it, shouldSelect = it == selectedTitle)
            }.toPersistentList(),
            onSelected = { selectedTitle = it.title },
            onDismissRequest = { showPopup = false },
        )
    }
}

@Composable
private fun CategoryNaviContent(
    selectedTitle: String,
    backIcon: SDGCategoryNaviLeftIconItem,
    rightIcons: PersistentList<SDGCategoryNaviIconItem>,
    hasDropdown: SDGCategoryNaviHasDropdown,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SDGSpacing.Spacing40),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing20),
    ) {
        SDGCategoryNavi(
            title = selectedTitle,
            leftIcon = backIcon,
            rightIcons = rightIcons,
            backgroundColor = SDGColor.Neutral100,
            hasDropdown = hasDropdown,
        )
        SDGCategoryNavi(
            title = "뒤로가기 아이콘이 없는 경우",
            leftIcon = null,
            rightIcons = persistentListOf(rightIcons[1]),
            backgroundColor = SDGColor.Neutral100,
            hasDropdown = hasDropdown,
        )
        SDGCategoryNavi(
            title = "드롭다운이 없는 경우",
            leftIcon = backIcon,
            rightIcons = persistentListOf(rightIcons[0], rightIcons[1]),
            backgroundColor = SDGColor.Neutral100,
            hasDropdown = SDGCategoryNaviHasDropdown.False,
        )
        SDGCategoryNavi(
            title = "긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요",
            leftIcon = backIcon,
            rightIcons = rightIcons,
            backgroundColor = SDGColor.Neutral100,
            hasDropdown = hasDropdown,
        )
        SDGCategoryNavi(
            title = "우측 아이콘이 없는 긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요",
            leftIcon = backIcon,
            rightIcons = null,
            backgroundColor = SDGColor.Neutral100,
            hasDropdown = hasDropdown,
        )
        SDGCategoryNavi(
            title = selectedTitle,
            leftIcon = backIcon.copy(iconColor = SDGColor.Neutral0),
            rightIcons = rightIcons.map {
                it.copy(iconColor = SDGColor.Neutral0)
            }.toPersistentList(),
            backgroundColor = SDGColor.Primary300,
            titleColor = SDGColor.Neutral0,
            hasDropdown = hasDropdown,
        )
    }
}

@Preview
@Composable
private fun PreviewCategoryNaviScreen() {
    ShoplDesignGuideTheme {
        CategoryNaviScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
