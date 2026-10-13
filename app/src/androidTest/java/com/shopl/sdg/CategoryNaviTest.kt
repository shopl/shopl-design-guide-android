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
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
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
    fun shortTitleKeepsDropdownAdjacentAndDispatchesEachAction() {
        val clicks = IntArray(3)
        rule.setContent {
            Box(Modifier.width(375.dp).testTag("navi")) {
                SDGCategoryNavi(
                    title = "Label",
                    leftIcon = icon("back") { clicks[0]++ },
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
        val dropdown = rule.onNodeWithContentDescription("Label")
        dropdown.assertWidthIsEqualTo(20.dp).assertHeightIsEqualTo(20.dp).performClick()
        val titleBounds = rule.onNodeWithText("Label").getUnclippedBoundsInRoot()
        assertEquals(titleBounds.right, dropdown.getUnclippedBoundsInRoot().left)
        assertEquals(listOf(1, 1, 1), clicks.toList())
    }

    @Test
    fun longTitleEllipsizesWithoutDisplacingDropdownOrRightIcons() {
        val count = mutableStateOf(3)
        val longTitle = "긴 카테고리 이름이 들어가는 경우 말줄임표시 확인 필요 ".repeat(4)
        rule.setContent {
            Box(Modifier.width(320.dp).testTag("navi")) {
                SDGCategoryNavi(
                    title = longTitle,
                    leftIcon = icon("back"),
                    rightIcons = List(count.value) { icon("right$it") }.toPersistentList(),
                    backgroundColor = SDGColor.Neutral100,
                    hasDropdown = SDGCategoryNaviHasDropdown.True(onClick = {}),
                )
            }
        }
        var previousWidth = 0.dp
        for (iconCount in 3 downTo 0) {
            rule.runOnIdle { count.value = iconCount }
            val title = rule.onNodeWithText(longTitle)
            val layouts = mutableListOf<TextLayoutResult>()
            title.performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
            assertEquals(1, layouts.single().lineCount)
            assertTrue(layouts.single().isLineEllipsized(0))
            val bounds = title.getUnclippedBoundsInRoot()
            assertTrue(bounds.right - bounds.left > previousWidth)
            previousWidth = bounds.right - bounds.left
            val dropdown = rule.onNodeWithContentDescription(longTitle)
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
    fun hiddenControlsAndMarginsKeepTitleAtDesignInsetWithLargeFont() {
        rule.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, 1.5f)) {
                Box(Modifier.width(375.dp).testTag("navi")) {
                    SDGCategoryNavi(
                        title = "Label",
                        leftIcon = null,
                        rightIcons = persistentListOf(),
                        backgroundColor = SDGColor.Neutral100,
                        hasDropdown = SDGCategoryNaviHasDropdown.False,
                        marginValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    )
                }
            }
        }
        rule.onNodeWithContentDescription("Label").assertDoesNotExist()
        rule.onNodeWithTag("navi").assertHeightIsEqualTo(64.dp)
        val titleBounds = rule.onNodeWithText("Label").getUnclippedBoundsInRoot()
        val naviBounds = rule.onNodeWithTag("navi").getUnclippedBoundsInRoot()
        assertEquals(naviBounds.left + 28.dp, titleBounds.left)
        assertTrue(titleBounds.top >= naviBounds.top + 8.dp)
        assertTrue(titleBounds.bottom <= naviBounds.bottom - 8.dp)
    }

    @Test
    fun dropdownRemainsVisibleWhenTitleAndIconsAreHidden() {
        rule.setContent {
            SDGCategoryNavi(
                title = null,
                leftIcon = null,
                rightIcons = null,
                backgroundColor = SDGColor.Neutral100,
                hasDropdown = SDGCategoryNaviHasDropdown.True(onClick = {}),
            )
        }
        val selectDescription = InstrumentationRegistry.getInstrumentation().targetContext
            .getString(SDGResource.string.select)
        rule.onNodeWithContentDescription(selectDescription).assertIsDisplayed()
            .assertWidthIsEqualTo(20.dp).assertHeightIsEqualTo(20.dp)
    }

    @Test
    fun moreThanThreeRightIconsIsRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            rule.setContent {
                SDGCategoryNavi(
                    title = "Label",
                    leftIcon = null,
                    rightIcons = List(4) { icon("right$it") }.toPersistentList(),
                    backgroundColor = SDGColor.Neutral100,
                    hasDropdown = SDGCategoryNaviHasDropdown.True(onClick = {}),
                )
            }
        }
    }

    @Test
    fun sampleDropdownShowsListPopupAndUpdatesTitle() {
        rule.setContent { CategoryNaviScreen(onClickBack = {}, onClickMenu = {}) }
        rule.onAllNodesWithContentDescription("Label")[0].performClick()
        rule.onNodeWithText("다른 카테고리").assertIsDisplayed().performClick()
        rule.onAllNodesWithText("다른 카테고리")[0].assertIsDisplayed()
        rule.onAllNodesWithContentDescription("다른 카테고리")[0].assertIsDisplayed()
    }

    private fun icon(description: String, onClick: () -> Unit = {}) = SDGCategoryNaviIconItem(
        iconResId = SDGResource.drawable.ic_navi_filter,
        onClick = onClick,
        contentDescription = description,
    )
}
