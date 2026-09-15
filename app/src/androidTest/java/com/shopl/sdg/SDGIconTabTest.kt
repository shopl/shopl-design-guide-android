package com.shopl.sdg

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.tab.icon.SDGIconTab
import com.shopl.sdg.component.tab.icon.SDGIconTabIcon
import com.shopl.sdg.component.tab.icon.SDGIconTabIconSize
import com.shopl.sdg.component.tab.icon.SDGIconTabOption
import com.shopl.sdg.component.tab.icon.SDGTabItem
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.toPersistentList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SDGIconTabTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun optionCountsApplyOuterPaddingAndKeepTabDimensions() {
        val option = mutableStateOf(option(tabs(3)))
        val selectedTab = mutableIntStateOf(1)
        composeRule.setContent {
            Box(modifier = Modifier
                .width(335.dp)
                .testTag("Icon Tab container")) {
                SDGIconTab(
                    option = option.value,
                    selectedTab = selectedTab.intValue,
                    onTabClick = {},
                    paddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                )
            }
        }

        for (optionCount in 3..5) {
            for (selection in 1..optionCount) {
                composeRule.runOnIdle {
                    option.value = option(tabs(optionCount))
                    selectedTab.intValue = selection
                }
                val tabNodes = composeRule.onAllNodes(isSelectable())
                tabNodes.assertCountEquals(optionCount)
                // 각 고정 너비와 간격은 화면 밀도에 따라 개별 픽셀로 반올림됩니다.
                val selectedWidth = with(composeRule.density) {
                    (
                            335.dp.roundToPx() - 2 * 16.dp.roundToPx() -
                                    (optionCount - 1) * (50.dp.roundToPx() + 4.dp.roundToPx())
                            ).toDp()
                }
                val container = composeRule.onNodeWithTag("Icon Tab container")
                val containerBounds = container.fetchSemanticsNode().boundsInRoot
                val firstTabBounds = tabNodes[0].fetchSemanticsNode().boundsInRoot
                with(composeRule.density) {
                    assertEquals(
                        16.dp.roundToPx().toFloat(),
                        firstTabBounds.left - containerBounds.left,
                        1f
                    )
                    assertEquals(
                        8.dp.roundToPx().toFloat(),
                        firstTabBounds.top - containerBounds.top,
                        1f
                    )
                    container.assertHeightIsEqualTo((76.dp.roundToPx() + 2 * 8.dp.roundToPx()).toDp())
                }
                for (index in 0 until optionCount) {
                    tabNodes[index].assertHeightIsEqualTo(76.dp)
                    if (index + 1 == selection) {
                        tabNodes[index].assertIsSelected().assertWidthIsEqualTo(selectedWidth)
                    } else {
                        tabNodes[index].assertIsNotSelected().assertWidthIsEqualTo(50.dp)
                    }
                }
            }
        }
    }

    @Test
    fun clickReportsOneBasedPositionAndSelectionFollowsCallerState() {
        val tabs = tabs(3)
        val option = SDGIconTabOption.ThreeOption(tabs = tabs)
        val selectedTab = mutableIntStateOf(1)
        var clickedTab = -1
        composeRule.setContent {
            Box(modifier = Modifier.width(335.dp)) {
                SDGIconTab(
                    option = option,
                    selectedTab = selectedTab.intValue,
                    onTabClick = { clickedTab = it },
                )
            }
        }

        composeRule.onNodeWithContentDescription("Label 3").performClick()
        composeRule.runOnIdle { assertEquals(3, clickedTab) }
        composeRule.onNodeWithText("Label 1").assertIsSelected()
        composeRule.onNodeWithContentDescription("Label 3").assertIsNotSelected()

        composeRule.runOnIdle { selectedTab.intValue = clickedTab }
        composeRule.onNodeWithText("Label 3").assertIsSelected()
        composeRule.onNodeWithContentDescription("Label 1").assertIsNotSelected()
        tabs.forEach { composeRule.onAllNodesWithText(it.count).assertCountEquals(1) }
        composeRule.runOnIdle { assertEquals(tabs, option.tabs) }
    }

    @Test
    fun commonCountAndShowCountArePreservedAcrossSelectionChanges() {
        val tabs = tabs(4)
        val option = SDGIconTabOption.FourOption(
            tabs = listOf(
                tabs[0].copy(count = "0"),
                tabs[1].copy(count = "N"),
                tabs[2].copy(count = "999+"),
                tabs[3].copy(count = "hidden", showCount = false),
            ),
        )
        val selectedTab = mutableIntStateOf(1)
        composeRule.setContent {
            Box(modifier = Modifier.width(335.dp)) {
                SDGIconTab(
                    option = option,
                    selectedTab = selectedTab.intValue,
                    onTabClick = {},
                )
            }
        }

        for (selection in 1..4) {
            composeRule.runOnIdle { selectedTab.intValue = selection }
            for (count in listOf("0", "N", "999+")) {
                composeRule.onAllNodesWithText(count).assertCountEquals(1)
            }
            composeRule.onAllNodesWithText("hidden").assertCountEquals(0)
            val layouts = mutableListOf<TextLayoutResult>()
            composeRule.onNodeWithText("999+", useUnmergedTree = true)
                .performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
            assertEquals(1, layouts.single().lineCount)
            assertFalse(layouts.single().hasVisualOverflow)
        }
    }

    @Test
    fun longLabelUsesTwoLinesAndEllipsisWithoutHidingCount() {
        val label = "긴 라벨은 두 줄까지 표시하고 이후 말줄임 처리합니다. 추가 설명입니다."
        val tabs = tabs(5).mapIndexed { index, tab ->
            if (index == 0) tab.copy(label = label, count = "999+") else tab
        }
        composeRule.setContent {
            Box(modifier = Modifier.width(335.dp)) {
                SDGIconTab(
                    option = SDGIconTabOption.FiveOption(tabs = tabs),
                    selectedTab = 1,
                    onTabClick = {},
                )
            }
        }

        val layouts = mutableListOf<TextLayoutResult>()
        composeRule.onNodeWithText(label, useUnmergedTree = true)
            .performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
        assertEquals(2, layouts.single().lineCount)
        assertTrue(layouts.single().isLineEllipsized(1))
        composeRule.onAllNodesWithText("999+").assertCountEquals(1)
        composeRule.onNodeWithText(label).assertHeightIsEqualTo(76.dp)
    }

    @Test
    fun iconSizesAreCenteredInTheSameAreaWithoutCounts() {
        val tabs = tabs(4).map { it.copy(showCount = false) }
        composeRule.setContent {
            Box(modifier = Modifier.width(335.dp)) {
                SDGIconTab(
                    option = SDGIconTabOption.FourOption(tabs = tabs),
                    selectedTab = 4,
                    onTabClick = {},
                )
            }
        }

        SDGIconTabIconSize.entries.forEachIndexed { index, size ->
            val icon = composeRule.onNodeWithContentDescription(
                "Label ${index + 1}",
                useUnmergedTree = true
            )
            icon.assertWidthIsEqualTo(size.size).assertHeightIsEqualTo(size.size)
            val iconBounds = icon.fetchSemanticsNode().boundsInRoot
            val tabBounds =
                composeRule.onAllNodes(isSelectable())[index].fetchSemanticsNode().boundsInRoot
            assertEquals(tabBounds.center.x, iconBounds.center.x, 1f)
            assertEquals(tabBounds.center.y, iconBounds.center.y, 1f)
        }
        tabs.forEach { composeRule.onAllNodesWithText(it.count).assertCountEquals(0) }
    }

    @Test
    fun optionsRejectMismatchedTabCounts() {
        assertThrows(IllegalArgumentException::class.java) { SDGIconTabOption.ThreeOption(tabs(4)) }
        assertThrows(IllegalArgumentException::class.java) { SDGIconTabOption.FourOption(tabs(3)) }
        assertThrows(IllegalArgumentException::class.java) { SDGIconTabOption.FiveOption(tabs(4)) }
    }

    @Test
    fun optionsKeepTabsWhenSourceListsChange() {
        for (optionCount in 3..5) {
            val originalTabs = tabs(optionCount)
            for (source in listOf(originalTabs.toMutableList(), originalTabs.toMutableStateList())) {
                assertTabsUnaffectedBySourceChanges(source = source, option = option(source))
            }
        }
    }

    @Test
    fun copiedOptionsKeepTabsWhenSourceListsChange() {
        for (optionCount in 3..5) {
            val original = option(tabs(optionCount))
            val replacementTabs = tabs(optionCount).map { it.copy(label = "수정된 ${it.label}") }
            for (source in listOf(replacementTabs.toMutableList(), replacementTabs.toMutableStateList())) {
                val copied = when (original) {
                    is SDGIconTabOption.ThreeOption -> original.copy(tabs = source)
                    is SDGIconTabOption.FourOption -> original.copy(tabs = source)
                    is SDGIconTabOption.FiveOption -> original.copy(tabs = source)
                }
                assertTabsUnaffectedBySourceChanges(source = source, option = copied)
                assertEquals(tabs(optionCount), original.tabs)
            }
        }
    }

    @Test
    fun copiedOptionsRejectMismatchedTabCounts() {
        for (optionCount in 3..5) {
            val original = option(tabs(optionCount))
            val invalidTabs = tabs(optionCount + 1)
            assertThrows(IllegalArgumentException::class.java) {
                when (original) {
                    is SDGIconTabOption.ThreeOption -> original.copy(tabs = invalidTabs)
                    is SDGIconTabOption.FourOption -> original.copy(tabs = invalidTabs)
                    is SDGIconTabOption.FiveOption -> original.copy(tabs = invalidTabs)
                }
            }
            assertThrows(IllegalArgumentException::class.java) {
                when (original) {
                    is SDGIconTabOption.ThreeOption -> original.copy(tabs = invalidTabs.toPersistentList())
                    is SDGIconTabOption.FourOption -> original.copy(tabs = invalidTabs.toPersistentList())
                    is SDGIconTabOption.FiveOption -> original.copy(tabs = invalidTabs.toPersistentList())
                }
            }
        }
    }

    @Test
    fun selectedTabRejectsZeroBasedPosition() {
        assertThrows(IllegalArgumentException::class.java) {
            composeRule.setContent {
                SDGIconTab(
                    option = SDGIconTabOption.ThreeOption(tabs(3)),
                    selectedTab = 0,
                    onTabClick = {})
            }
        }
    }

    @Test
    fun selectedTabRejectsPositionPastOptionSize() {
        assertThrows(IllegalArgumentException::class.java) {
            composeRule.setContent {
                SDGIconTab(
                    option = SDGIconTabOption.ThreeOption(tabs(3)),
                    selectedTab = 4,
                    onTabClick = {})
            }
        }
    }

    private fun assertTabsUnaffectedBySourceChanges(
        source: MutableList<SDGTabItem>,
        option: SDGIconTabOption,
    ) {
        val originalTabs = source.toList()
        source.add(source.first())
        assertEquals(originalTabs, option.tabs)
        source.removeAt(0)
        assertEquals(originalTabs, option.tabs)
        source[0] = source.first().copy(label = "변경된 라벨", count = "999+", showCount = false)
        assertEquals(originalTabs, option.tabs)
        source.clear()
        assertEquals(originalTabs, option.tabs)
    }

    private fun option(tabs: List<SDGTabItem>): SDGIconTabOption = when (tabs.size) {
        3 -> SDGIconTabOption.ThreeOption(tabs = tabs)
        4 -> SDGIconTabOption.FourOption(tabs = tabs)
        5 -> SDGIconTabOption.FiveOption(tabs = tabs)
        else -> error("테스트에서 지원하지 않는 탭 개수입니다.")
    }

    private fun tabs(optionCount: Int): List<SDGTabItem> =
        List(optionCount) { index ->
            SDGTabItem(
                label = "Label ${index + 1}",
                count = "${(index + 1) * 10}",
                showCount = true,
                iconTabIc = SDGIconTabIcon(
                    icon = R.drawable.ic_common_list,
                    size = SDGIconTabIconSize.entries[index % SDGIconTabIconSize.entries.size],
                ),
            )
        }
}
