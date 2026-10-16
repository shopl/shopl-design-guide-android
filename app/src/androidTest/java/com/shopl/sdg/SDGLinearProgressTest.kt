package com.shopl.sdg

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shopl.sdg.component.progress.linear.SDGLinearProgress
import com.shopl.sdg.component.progress.linear.SDGLinearProgressType
import com.shopl.sdg.component.progress.linear.SDGLinearProgressType.Determinate
import com.shopl.sdg.component.progress.linear.SDGLinearProgressType.Indeterminate
import com.shopl.sdg_common.foundation.SDGColor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val PROGRESS_TAG = "linearProgress"

@RunWith(AndroidJUnit4::class)
class SDGLinearProgressTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun determinateProgressUpdatesFill() {
        val progress = mutableStateOf(0f)
        setContent {
            SDGLinearProgress(type = Determinate(progress.value), label = null, labelColor = SDGColor.Neutral700)
        }

        for (value in listOf(0f, 0.5f, 1f)) {
            composeRule.runOnIdle { progress.value = value }
            val node = composeRule.onNodeWithTag(PROGRESS_TAG)
            val pixels = node.captureToImage().toPixelMap()
            val leftColor = if (value == 0f) SDGColor.Neutral300 else SDGColor.Primary300
            val rightColor = if (value == 1f) SDGColor.Primary300 else SDGColor.Neutral300
            assertEquals(leftColor, pixels[pixels.width / 4, pixels.height / 2])
            assertEquals(rightColor, pixels[pixels.width * 3 / 4, pixels.height / 2])
        }
    }

    @Test
    fun absentLabelLeavesOnlyTheFourDpBar() {
        setContent {
            SDGLinearProgress(type = Determinate(0.5f), label = null, labelColor = SDGColor.Neutral700)
        }

        composeRule.onNodeWithTag(PROGRESS_TAG)
            .assertWidthIsEqualTo(160.dp)
            .assertHeightIsEqualTo(4.dp)
    }

    @Test
    fun labelPreservesTheThirtyDpHeight() {
        setContent {
            SDGLinearProgress(type = Determinate(0.5f), label = "업로드 중입니다", labelColor = SDGColor.Neutral700)
        }

        composeRule.onNodeWithText("업로드 중입니다").assertExists()
        composeRule.onNodeWithTag(PROGRESS_TAG)
            .assertHeightIsEqualTo(30.dp)
    }

    @Test
    fun indeterminateIndicatorKeepsItsLengthAndReverses() {
        composeRule.mainClock.autoAdvance = false
        setContent {
            SDGLinearProgress(type = Indeterminate, label = null, labelColor = SDGColor.Neutral700)
        }

        val node = composeRule.onNodeWithTag(PROGRESS_TAG)
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
    fun switchingModesUpdatesTheIndicatorWidth() {
        composeRule.mainClock.autoAdvance = false
        val type = mutableStateOf<SDGLinearProgressType>(Indeterminate)
        setContent {
            SDGLinearProgress(type = type.value, label = null, labelColor = SDGColor.Neutral700)
        }

        for ((value, expectedFraction) in listOf(
            Determinate(0.25f) to 0.25f,
            Indeterminate to 0.5f,
            Determinate(1f) to 1f,
        )) {
            composeRule.runOnIdle { type.value = value }
            composeRule.mainClock.advanceTimeByFrame()
            val image = composeRule.onNodeWithTag(PROGRESS_TAG).captureToImage()
            assertEquals(
                image.width * expectedFraction,
                image.indicatorRange().count().toFloat(),
                2f,
            )
        }
    }

    @Test
    fun longLabelWrapsInANarrowParentAtLargeFontScale() {
        val label = "파일을 업로드하고 있습니다. 잠시만 기다려 주세요."
        setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, fontScale = 3f)) {
                Box(Modifier.width(100.dp)) {
                    SDGLinearProgress(type = Determinate(0.5f), label = label, labelColor = SDGColor.Neutral700)
                }
            }
        }

        composeRule.onNodeWithTag(PROGRESS_TAG).assertWidthIsEqualTo(100.dp)
        val results = mutableListOf<TextLayoutResult>()
        composeRule.onNodeWithText(label, useUnmergedTree = true)
            .performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(results) }
        assertTrue(results.single().lineCount > 1)
        assertFalse(results.single().hasVisualOverflow)
    }

    @Test
    fun rtlProgressFillsFromTheLeft() {
        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                SDGLinearProgress(type = Determinate(0.5f), label = null, labelColor = SDGColor.Neutral700)
            }
        }

        val pixels = composeRule.onNodeWithTag(PROGRESS_TAG).captureToImage().toPixelMap()
        assertEquals(SDGColor.Primary300, pixels[pixels.width / 4, pixels.height / 2])
        assertEquals(SDGColor.Neutral300, pixels[pixels.width * 3 / 4, pixels.height / 2])
    }

    @Test
    fun marginsStayOutsideTheComponentBounds() {
        setContent {
            SDGLinearProgress(
                type = Determinate(0.5f),
                label = null,
                labelColor = SDGColor.Neutral700,
                marginValues = PaddingValues(start = 12.dp, top = 8.dp),
            )
        }

        val node = composeRule.onNodeWithTag(PROGRESS_TAG)
            .assertWidthIsEqualTo(172.dp)
            .assertHeightIsEqualTo(12.dp)
        val pixels = node.captureToImage().toPixelMap()
        with(composeRule.density) {
            assertEquals(SDGColor.Neutral0, pixels[6.dp.roundToPx(), 10.dp.roundToPx()])
            assertEquals(SDGColor.Neutral0, pixels[52.dp.roundToPx(), 4.dp.roundToPx()])
            assertEquals(SDGColor.Primary300, pixels[52.dp.roundToPx(), 10.dp.roundToPx()])
        }
    }

    private fun setContent(content: @Composable () -> Unit) {
        composeRule.setContent {
            Box(Modifier.testTag(PROGRESS_TAG).background(SDGColor.Neutral0)) {
                content()
            }
        }
    }

    private fun ImageBitmap.indicatorRange(): IntRange {
        val pixels = toPixelMap()
        val filled = (0 until width).filter { pixels[it, height / 2] == SDGColor.Primary300 }
        assertTrue("진행 인디케이터가 표시되어야 합니다.", filled.isNotEmpty())
        return filled.first()..filled.last()
    }
}
