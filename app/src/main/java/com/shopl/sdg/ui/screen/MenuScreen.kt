package com.shopl.sdg.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.navigation.basic.SDGBasicNavi
import com.shopl.sdg.component.navigation.basic.SDGBasicNaviIconItem
import com.shopl.sdg.scene.SDGScene
import com.shopl.sdg.ui.SDGScaffold
import com.shopl.sdg.ui.screen.model.MenuItemUiModel
import com.shopl.sdg.ui.screen.model.MenuUiModel
import com.shopl.sdg.ui.screen.model.componentMenuSection
import com.shopl.sdg.ui.screen.model.foundationMenuSection
import com.shopl.sdg.ui.screen.model.templateMenuSection
import com.shopl.sdg.ui.screen.model.toMenuItemUiModels
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * SDG Sample App - Menu
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3120&m=dev">Figma</a>
 */

private const val GROUP_ROTATE_EXPANDED = 180f
private const val GROUP_ROTATE_COLLAPSED = 0f

@Composable
internal fun MenuScreen(
    fromScene: SDGScene?,
    moveToScene: (SDGScene) -> Unit,
    moveToBack: () -> Unit,
) {

    val listState = rememberLazyListState()

    var expandedGroups by remember {
        mutableStateOf(
            findExpandedGroupForScene(fromScene)
        )
    }

    val onGroupClick: (String) -> Unit = { groupName ->
        expandedGroups = if (expandedGroups.contains(groupName)) {
            expandedGroups - groupName
        } else {
            expandedGroups + groupName
        }
    }

    val currentMenuItems = remember(expandedGroups) {
        (foundationMenuSection.toMenuItemUiModels(expandedGroups) +
                componentMenuSection.toMenuItemUiModels(expandedGroups) +
                templateMenuSection.toMenuItemUiModels(expandedGroups)).toImmutableList()
    }

    MenuFocusEffect(fromScene, currentMenuItems, listState)

    SDGScaffold(
        backgroundColor = SDGColor.Neutral0,
        topBar = {
            SDGBasicNavi(
                title = null,
                backgroundColor = SDGColor.Neutral0,
                leftIcon = SDGBasicNaviIconItem(
                    resId = R.drawable.ic_navi_close,
                    color = SDGColor.Neutral700,
                    onClick = { moveToBack() }
                ),
                rightIcons = null
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(
                top = SDGSpacing.Spacing10,
                bottom = SDGSpacing.Spacing40
            ),
            state = listState
        ) {
            item {
                HeaderContent()
                Spacer(modifier = Modifier.height(SDGSpacing.Spacing8))
            }

            item {
                SceneContent(
                    displayLabel = SDGScene.Overview.displayLabel,
                    onClick = {
                        moveToScene(SDGScene.Overview)
                    }
                )
            }

            items(currentMenuItems, key = { it.hashCode() }) { item ->
                when (item) {
                    is MenuItemUiModel.SectionItem -> {
                        SectionContent(
                            displayLabel = item.displayLabel,
                        )
                    }

                    is MenuItemUiModel.GroupItem -> {
                        GroupContent(
                            displayLabel = item.displayLabel,
                            isExpanded = expandedGroups.contains(item.displayLabel),
                            hasImplementItems = item.hasImplementItems,
                            onClick = {
                                onGroupClick(item.displayLabel)
                            }
                        )
                    }

                    is MenuItemUiModel.SceneItem -> {
                        SceneContent(
                            displayLabel = item.scene.displayLabel,
                            isImplemented = item.scene.implemented,
                            isGroupingScene = item.isGroupingScene,
                            isSelected = item.scene == fromScene,
                            onClick = {
                                if (item.scene == fromScene) moveToBack()
                                else moveToScene(item.scene)
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * 특정 Scene이 속한 그룹을 찾아서 반환하는 함수
 */
private fun findExpandedGroupForScene(fromScene: SDGScene?): Set<String> {
    if (fromScene == null) return emptySet()
    val expandedSet = mutableSetOf<String>()
    val allSections = listOf(foundationMenuSection, componentMenuSection, templateMenuSection)
    allSections.forEach { section ->
        section.menus.forEach { menu ->
            when (menu) {
                is MenuUiModel.GroupMenu -> {
                    val hasScene = menu.sceneMenus.any { it.scene == fromScene }
                    if (hasScene) {
                        expandedSet.add(menu.groupName)
                    }
                }

                is MenuUiModel.SceneMenu -> {
                    // GroupMenu가 아닌 경우 무시
                }
            }
        }
    }
    return expandedSet
}

/**
 * 특정 Scene이 속한 그룹의 상단으로 스크롤을 맞춰주는 효과
 */
@Composable
private fun MenuFocusEffect(
    fromScene: SDGScene?,
    items: ImmutableList<MenuItemUiModel>,
    listState: LazyListState
) {
    LaunchedEffect(fromScene) {
        val sceneIndex =
            items.indexOfFirst { it is MenuItemUiModel.SceneItem && it.scene == fromScene }
        if (sceneIndex != -1) {
            var targetIndex = sceneIndex
            for (i in sceneIndex downTo 0) {
                if (items[i] is MenuItemUiModel.GroupItem) {
                    targetIndex = i
                    break
                }
            }
            // Header + Overview 고려하여 offset +2
            listState.scrollToItem(targetIndex + 2)
        }
    }
}

@Composable
private fun SectionContent(
    displayLabel: String
) {
    SDGText(
        modifier = Modifier.padding(
            start = SDGSpacing.Spacing16,
            top = SDGSpacing.Spacing28,
            end = SDGSpacing.Spacing16,
        ),
        text = displayLabel,
        textColor = SDGColor.Neutral350,
        typography = SDGTypography.Body2SB
    )
}

@Composable
private fun GroupContent(
    displayLabel: String,
    isExpanded: Boolean,
    hasImplementItems: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                start = SDGSpacing.Spacing16,
                top = SDGSpacing.Spacing8,
                end = SDGSpacing.Spacing16,
            )
            .height(40.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing4)
    ) {
        SDGText(
            text = displayLabel,
            textColor = if (hasImplementItems) SDGColor.Neutral700 else SDGColor.Neutral300,
            typography = SDGTypography.Title2SB
        )
        SDGImage(
            modifier = Modifier.rotate(if (isExpanded) GROUP_ROTATE_EXPANDED else GROUP_ROTATE_COLLAPSED),
            resId = R.drawable.ic_common_triangledown,
            color = SDGColor.Neutral350
        )
    }
}

@Composable
private fun SceneContent(
    displayLabel: String,
    isGroupingScene: Boolean = false,
    isImplemented: Boolean = true,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(
                start = SDGSpacing.Spacing16,
                top = SDGSpacing.Spacing8,
                end = SDGSpacing.Spacing16,
            )
            .height(40.dp)
            .clickable {
                if (isImplemented) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (isGroupingScene) {
            SDGText(
                modifier = Modifier.padding(start = SDGSpacing.Spacing8),
                text = displayLabel,
                textColor = when {
                    !isImplemented -> SDGColor.Neutral300
                    isSelected -> SDGColor.Primary300
                    else -> SDGColor.Neutral600
                },
                typography = SDGTypography.Title2SB,
            )
        } else {
            SDGText(
                text = displayLabel,
                textColor = when {
                    !isImplemented -> SDGColor.Neutral300
                    isSelected -> SDGColor.Primary300
                    else -> SDGColor.Neutral700
                },
                typography = SDGTypography.Title2SB,
            )
        }
    }
}

@Composable
private fun HeaderContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(color = SDGColor.Neutral200)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SDGSpacing.Spacing16,
                    top = SDGSpacing.Spacing10,
                    end = SDGSpacing.Spacing16,
                    bottom = SDGSpacing.Spacing20,
                ),
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8)
        ) {
            SDGText(
                text = "SDG",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Point1SB
            )
            SDGText(
                text = "Shopl Design Guide",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body2R
            )
        }
        HorizontalDivider(color = SDGColor.Neutral700)
    }
}

@Preview
@Composable
private fun PreviewMenuScreen() {
    ShoplDesignGuideTheme {
        MenuScreen(
            fromScene = null,
            moveToScene = {},
            moveToBack = {}
        )
    }
}
