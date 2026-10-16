package com.shopl.sdg

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shopl.sdg.component.progress.linear.SDGLinearProgress
import com.shopl.sdg_common.foundation.SDGColor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SDGLinearProgressTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val hasProgress = SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo)

    @Test
    fun determinateProgressUpdatesSemanticsAndFill() {
        val progress = mutableStateOf(0f)
        composeRule.setContent { SDGLinearProgress(progress = progress.value) }

        for (value in listOf(0f, 0.5f, 1f)) {
            composeRule.runOnIdle { progress.value = value }
            val node = composeRule.onNode(hasProgress)
            node.assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.ProgressBarRangeInfo,
                    ProgressBarRangeInfo(value, 0f..1f),
                ),
            )
            val pixels = node.captureToImage().toPixelMap()
            val leftColor = if (value == 0f) SDGColor.Neutral300 else SDGColor.Primary300
            val rightColor = if (value == 1f) SDGColor.Primary300 else SDGColor.Neutral300
            assertEquals(leftColor, pixels[pixels.width / 4, pixels.height / 2])
            assertEquals(rightColor, pixels[pixels.width * 3 / 4, pixels.height / 2])
        }
    }

    @Test
    fun absentLabelLeavesOnlyTheFourDpBar() {
        composeRule.setContent { SDGLinearProgress(progress = 0.5f) }

        composeRule.onNode(hasProgress)
            .assertWidthIsEqualTo(160.dp)
            .assertHeightIsEqualTo(4.dp)
    }

    @Test
    fun labelAndProgressShareOneAccessibilityNode() {
        composeRule.setContent {
            SDGLinearProgress(progress = 0.5f, label = "업로드 중입니다")
        }

        composeRule.onAllNodes(hasProgress).assertCountEquals(1)
        composeRule.onNode(hasProgress)
            .assertTextEquals("업로드 중입니다")
            .assertHeightIsEqualTo(30.dp)
    }

    @Test
    fun indeterminateIndicatorKeepsItsLengthAndReverses() {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent { SDGLinearProgress() }

        val node = composeRule.onNode(hasProgress)
        node.assert(
            SemanticsMatcher.expectValue(
                SemanticsProperties.ProgressBarRangeInfo,
                ProgressBarRangeInfo.Indeterminate,
            ),
        )
        composeRule.mainClock.advanceTimeByFrame()
        val start = node.captureToImage().indicatorRange()
        composeRule.mainClock.advanceTimeBy(500)
        val middle = node.captureToImage().indicatorRange()
        composeRule.mainClock.advanceTimeBy(500)
        val end = node.captureToImage().indicatorRange()
        composeRule.mainClock.advanceTimeBy(500)
        val returning = node.captureToImage().indicatorRange()

        assertTrue(start.first < middle.first)
        assertTrue(middle.first < end.first)
        assertTrue(returning.first < end.first)
        assertEquals(start.count().toFloat(), middle.count().toFloat(), 1f)
        assertEquals(start.count().toFloat(), end.count().toFloat(), 1f)
        assertEquals(start.count().toFloat(), returning.count().toFloat(), 1f)
    }

    @Test
    fun switchingModesReplacesTheProgressSemantics() {
        val progress = mutableStateOf<Float?>(null)
        composeRule.setContent { SDGLinearProgress(progress = progress.value) }

        for (value in listOf(0.5f, null, 1f)) {
            composeRule.runOnIdle { progress.value = value }
            composeRule.onNode(hasProgress).assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.ProgressBarRangeInfo,
                    value?.let { ProgressBarRangeInfo(it, 0f..1f) }
                        ?: ProgressBarRangeInfo.Indeterminate,
                ),
            )
        }
    }

    @Test
    fun longLabelWrapsInANarrowParentAtLargeFontScale() {
        val label = "파일을 업로드하고 있습니다. 잠시만 기다려 주세요."
        composeRule.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, fontScale = 3f)) {
                Box(Modifier.width(100.dp)) {
                    SDGLinearProgress(progress = 0.5f, label = label)
                }
            }
        }

        composeRule.onNode(hasProgress).assertWidthIsEqualTo(100.dp)
        val results = mutableListOf<TextLayoutResult>()
        composeRule.onNodeWithText(label, useUnmergedTree = true)
            .performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(results) }
        assertTrue(results.single().lineCount > 1)
        assertFalse(results.single().hasVisualOverflow)
    }

    @Test
    fun rtlProgressFillsFromTheRight() {
        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                SDGLinearProgress(progress = 0.5f)
            }
        }

        val pixels = composeRule.onNode(hasProgress).captureToImage().toPixelMap()
        assertEquals(SDGColor.Neutral300, pixels[pixels.width / 4, pixels.height / 2])
        assertEquals(SDGColor.Primary300, pixels[pixels.width * 3 / 4, pixels.height / 2])
    }

    @Test
    fun marginsStayOutsideTheComponentBounds() {
        composeRule.setContent {
            SDGLinearProgress(
                progress = 0.5f,
                marginValues = PaddingValues(start = 12.dp, top = 8.dp),
            )
        }

        composeRule.onNode(hasProgress)
            .assertLeftPositionInRootIsEqualTo(12.dp)
            .assertTopPositionInRootIsEqualTo(8.dp)
            .assertWidthIsEqualTo(160.dp)
            .assertHeightIsEqualTo(4.dp)
    }

    private fun ImageBitmap.indicatorRange(): IntRange {
        val pixels = toPixelMap()
        val filled = (0 until width).filter { pixels[it, height / 2] == SDGColor.Primary300 }
        assertTrue("진행 인디케이터가 표시되어야 합니다.", filled.isNotEmpty())
        return filled.first()..filled.last()
    }
}
