package com.shopl.sdg

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import com.shopl.sdg.component.navigation.category.SDGCategoryNavi
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviHasDropdown
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviIconItem
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviLabel
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviLeftIconItem
import com.shopl.sdg.ui.screen.template.CategoryNaviScreen
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R as SDGResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CategoryNaviTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun shortLabelKeepsDropdownAdjacentAndDispatchesEachAction() {
        val clicks = IntArray(3)
        val leftIcon = mutableStateOf<SDGCategoryNaviLeftIconItem?>(
            SDGCategoryNaviLeftIconItem(
                onClick = { clicks[0]++ },
                contentDescription = "back",
            ),
        )
        rule.setContent {
            Box(Modifier.width(375.dp).testTag("navi")) {
                SDGCategoryNavi(
                    label = SDGCategoryNaviLabel(text = "Label", color = SDGColor.Neutral900),
                    leftIcon = leftIcon.value,
                    rightIcons = persistentListOf(icon("right") { clicks[1]++ }),
                    backgroundColor = SDGColor.Neutral100,
                    hasDropdown = SDGCategoryNaviHasDropdown.True(onClick = { clicks[2]++ }),
                )
            }
        }
        rule.onNodeWithTag("navi").assertHeightIsEqualTo(48.dp)
        rule.onNodeWithContentDescription("back").assertWidthIsEqualTo(40.dp)
            .assertHeightIsEqualTo(40.dp).performClick()
        rule.onNodeWithContentDescription("right").assertWidthIsEqualTo(40.dp)
            .assertHeightIsEqualTo(40.dp).performClick()
        val dropdown = rule.onNode(dropdownMatcher())
        dropdown.assertWidthIsEqualTo(20.dp).assertHeightIsEqualTo(20.dp).performClick()
        val labelBounds = rule.onNodeWithText("Label").getUnclippedBoundsInRoot()
        assertEquals(labelBounds.right, dropdown.getUnclippedBoundsInRoot().left)
        assertEquals(listOf(1, 1, 1), clicks.toList())
        val naviBounds = rule.onNodeWithTag("navi").getUnclippedBoundsInRoot()
        assertEquals(naviBounds.left + 52.dp, labelBounds.left)

        rule.runOnIdle { leftIcon.value = null }
        rule.onNodeWithContentDescription("back").assertDoesNotExist()
        val labelWithoutBack = rule.onNodeWithText("Label").getUnclippedBoundsInRoot()
        assertEquals(naviBounds.left + 12.dp, labelWithoutBack.left)
    }

    @Test
    fun longLabelEllipsizesWithoutDisplacingDropdownOrRightIcons() {
        val count = mutableStateOf(3)
        val longLabelText = "긴 카테고리 이름이 들어가는 경우 말줄임표시 확인 필요 ".repeat(4)
        rule.setContent {
            Box(Modifier.width(320.dp).testTag("navi")) {
                SDGCategoryNavi(
                    label = SDGCategoryNaviLabel(text = longLabelText, color = SDGColor.Neutral900),
                    leftIcon = SDGCategoryNaviLeftIconItem(
                        onClick = {},
                        contentDescription = "back",
                    ),
                    rightIcons = List(count.value) { icon("right$it") }.toPersistentList(),
                    backgroundColor = SDGColor.Neutral100,
                    hasDropdown = SDGCategoryNaviHasDropdown.True(onClick = {}),
                )
            }
        }
        var previousWidth = 0.dp
        for (iconCount in 3 downTo 0) {
            rule.runOnIdle { count.value = iconCount }
            val label = rule.onNodeWithText(longLabelText)
            val layouts = mutableListOf<TextLayoutResult>()
            label.performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
            assertEquals(1, layouts.single().lineCount)
            assertTrue(layouts.single().isLineEllipsized(0))
            val bounds = label.getUnclippedBoundsInRoot()
            assertTrue(bounds.right - bounds.left > previousWidth)
            previousWidth = bounds.right - bounds.left
            val dropdown = rule.onNode(dropdownMatcher())
            dropdown.assertIsDisplayed().assertWidthIsEqualTo(20.dp)
            assertEquals(bounds.right, dropdown.getUnclippedBoundsInRoot().left)
            for (index in 0 until iconCount) {
                rule.onNodeWithContentDescription("right$index").assertIsDisplayed()
                    .assertWidthIsEqualTo(40.dp)
            }
            if (iconCount > 0) {
                val navi = rule.onNodeWithTag("navi").getUnclippedBoundsInRoot()
                val lastIcon = rule.onNodeWithContentDescription("right${iconCount - 1}")
                    .getUnclippedBoundsInRoot()
                assertEquals(navi.right - 10.dp, lastIcon.right)
            }
        }
    }

    @Test
    fun hiddenControlsAndMarginsKeepLabelAtDesignInsetWithLargeFont() {
        rule.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, 1.5f)) {
                Box(Modifier.width(375.dp).testTag("navi")) {
                    SDGCategoryNavi(
                        label = SDGCategoryNaviLabel(text = "Label", color = SDGColor.Neutral900),
                        leftIcon = null,
                        rightIcons = persistentListOf(),
                        backgroundColor = SDGColor.Neutral100,
                        hasDropdown = SDGCategoryNaviHasDropdown.False,
                        marginValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    )
                }
            }
        }
        rule.onNode(dropdownMatcher()).assertDoesNotExist()
        rule.onNodeWithTag("navi").assertHeightIsEqualTo(64.dp)
        val labelBounds = rule.onNodeWithText("Label").getUnclippedBoundsInRoot()
        val naviBounds = rule.onNodeWithTag("navi").getUnclippedBoundsInRoot()
        assertEquals(naviBounds.left + 28.dp, labelBounds.left)
        assertTrue(labelBounds.top >= naviBounds.top + 8.dp)
        assertTrue(labelBounds.bottom <= naviBounds.bottom - 8.dp)
    }

    @Test
    fun dropdownRemainsVisibleWhenLabelAndIconsAreHidden() {
        rule.setContent {
            SDGCategoryNavi(
                label = SDGCategoryNaviLabel(text = null, color = SDGColor.Neutral900),
                leftIcon = null,
                rightIcons = null,
                backgroundColor = SDGColor.Neutral100,
                hasDropdown = SDGCategoryNaviHasDropdown.True(onClick = {}),
            )
        }
        rule.onNode(dropdownMatcher()).assertIsDisplayed()
            .assertWidthIsEqualTo(20.dp).assertHeightIsEqualTo(20.dp)
    }

    @Test
    fun moreThanThreeRightIconsIsRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            rule.setContent {
                SDGCategoryNavi(
                    label = SDGCategoryNaviLabel(text = "Label", color = SDGColor.Neutral900),
                    leftIcon = null,
                    rightIcons = List(4) { icon("right$it") }.toPersistentList(),
                    backgroundColor = SDGColor.Neutral100,
                    hasDropdown = SDGCategoryNaviHasDropdown.True(onClick = {}),
                )
            }
        }
    }

    @Test
    fun sampleDropdownShowsListPopupAndUpdatesLabel() {
        rule.setContent { CategoryNaviScreen(onClickBack = {}, onClickMenu = {}) }
        rule.onAllNodes(dropdownMatcher())[0].performClick()
        rule.onNodeWithText("다른 카테고리").assertIsDisplayed().performClick()
        rule.onAllNodesWithText("다른 카테고리")[0].assertIsDisplayed()
        rule.onAllNodes(dropdownMatcher())[0].assertIsDisplayed()
    }

    private fun dropdownMatcher(): SemanticsMatcher {
        val selectLabel = InstrumentationRegistry.getInstrumentation().targetContext
            .getString(SDGResource.string.select)
        return SemanticsMatcher("드롭다운 선택 액션") {
            it.config.contains(SemanticsActions.OnClick) &&
                it.config[SemanticsActions.OnClick].label == selectLabel
        }
    }

    private fun icon(description: String, onClick: () -> Unit = {}) = SDGCategoryNaviIconItem(
        iconResId = SDGResource.drawable.ic_navi_filter,
        onClick = onClick,
        iconColor = null,
        showDot = false,
        contentDescription = description,
    )
}
