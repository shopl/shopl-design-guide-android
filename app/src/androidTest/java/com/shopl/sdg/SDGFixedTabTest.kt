package com.shopl.sdg

import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import com.shopl.sdg.component.tab.fixed.SDGFixedTab
import com.shopl.sdg.component.tab.fixed.SDGFixedTabOption
import com.shopl.sdg.component.tab.fixed.SDGFixedTabType
import com.shopl.sdg_common.foundation.SDGColor
import java.io.File
import kotlin.math.roundToInt
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SDGFixedTabTest {
    @get:Rule
    val compose = createEmptyComposeRule()

    private lateinit var host: ComponentActivity

    @After
    fun closeHost() {
        if (::host.isInitialized) compose.runOnUiThread { host.finish() }
    }

    @Test
    @Suppress("DEPRECATION")
    fun labelsSelectionAndUnderlines_followTheFixedTabGuide() {
        // ActivityScenario blocks devices using "Don't keep activities"; launch the host directly.
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        host = instrumentation.startActivitySync(
            Intent(instrumentation.targetContext, ComponentActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ) as ComponentActivity
        val labels = listOf("가용 영역을 초과하는 매우 긴 탭 라벨은 한 줄로 말줄임되어야 합니다", "Label 2", "Label 3")
        val tabCount = mutableIntStateOf(2)
        val selected = mutableIntStateOf(0)
        val padding = mutableStateOf(0.dp)
        val useLegacyApi = mutableStateOf(false)
        val underlineColor = SDGColor.Neutral200
        assertThrows(IllegalArgumentException::class.java) { SDGFixedTabOption.TwoOption(labels.take(1)) }
        assertThrows(IllegalArgumentException::class.java) { SDGFixedTabOption.ThreeOption(labels.take(2)) }
        compose.runOnUiThread {
            host.setContent {
                Box(modifier = Modifier.width(335.dp).testTag("fixedTab").background(SDGColor.Neutral0)) {
                    if (useLegacyApi.value) {
                        SDGFixedTab(
                            type = SDGFixedTabType.TwoOption(labels[0], labels[1]),
                            onTabClick = { selected.intValue = it },
                            unselectedTabUnderLineColor = underlineColor,
                            selectedTabIndex = selected.intValue,
                        )
                    } else {
                        SDGFixedTab(
                            option = if (tabCount.intValue == 2) {
                                SDGFixedTabOption.TwoOption(labels.take(2))
                            } else {
                                SDGFixedTabOption.ThreeOption(labels)
                            },
                            selectedTab = selected.intValue,
                            onTabClick = { selected.intValue = it },
                            unselectedTabUnderLineColor = underlineColor,
                            tabHorizontalPadding = padding.value,
                        )
                    }
                }
            }
        }

        for (count in listOf(2, 3)) {
            for (horizontalPadding in listOf(0.dp, 20.dp)) {
                compose.runOnIdle {
                    selected.intValue = 0
                    tabCount.intValue = count
                    padding.value = horizontalPadding
                }
                compose.onAllNodes(isSelectable()).assertCountEquals(count)
                val root = compose.onNodeWithTag("fixedTab")
                val bounds = root.fetchSemanticsNode().boundsInRoot
                val paddingPx = with(compose.density) { horizontalPadding.toPx() }
                val cellWidth = (bounds.width - 2 * paddingPx) / count
                assertEquals(with(compose.density) { 32.dp.toPx() }, bounds.height, 1f)

                val textLayouts = mutableListOf<TextLayoutResult>()
                compose.onNodeWithText(labels[0], useUnmergedTree = true)
                    .performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(textLayouts) }
                assertEquals(1, textLayouts.single().lineCount)
                assertTrue(textLayouts.single().isLineEllipsized(0))

                for (selectedIndex in 0 until count) {
                    compose.mainClock.advanceTimeBy(500)
                    compose.onNodeWithText(labels[selectedIndex]).performClick()
                    labels.take(count).forEachIndexed { index, label ->
                        val tab = compose.onNodeWithText(label)
                        tab.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab))
                        if (index == selectedIndex) tab.assertIsSelected() else tab.assertIsNotSelected()
                        val tabBounds = tab.fetchSemanticsNode().boundsInRoot
                        assertEquals(cellWidth, tabBounds.width, 1f)
                        assertEquals(bounds.left + paddingPx + index * cellWidth, tabBounds.left, 1f)
                        val labelBounds = compose.onNodeWithText(label, useUnmergedTree = true)
                            .fetchSemanticsNode().boundsInRoot
                        assertEquals(with(compose.density) { 8.dp.toPx() }, labelBounds.left - tabBounds.left, 1f)
                    }

                    val image = root.captureToImage()
                    val pixels = image.toPixelMap()
                    val selectedX = (paddingPx + (selectedIndex + 0.5f) * cellWidth).roundToInt()
                    val otherIndex = (selectedIndex + 1) % count
                    val otherX = (paddingPx + (otherIndex + 0.5f) * cellWidth).roundToInt()
                    val selectedLineTop = image.height - with(compose.density) { 2.dp.roundToPx() }
                    val baselineTop = image.height - with(compose.density) { 1.dp.roundToPx() }
                    assertEquals(SDGColor.Neutral700.toArgb(), pixels[selectedX, selectedLineTop].toArgb())
                    assertEquals(SDGColor.Neutral700.toArgb(), pixels[selectedX, image.height - 1].toArgb())
                    assertEquals(SDGColor.Neutral0.toArgb(), pixels[selectedX, selectedLineTop - 1].toArgb())
                    assertEquals(underlineColor.toArgb(), pixels[otherX, baselineTop].toArgb())
                    assertEquals(SDGColor.Neutral0.toArgb(), pixels[otherX, baselineTop - 1].toArgb())
                    if (horizontalPadding > 0.dp) {
                        assertEquals(underlineColor.toArgb(), pixels[0, image.height - 1].toArgb())
                        assertEquals(underlineColor.toArgb(), pixels[image.width - 1, image.height - 1].toArgb())
                    }
                    if (selectedIndex == count - 1 && horizontalPadding > 0.dp) {
                        val output = InstrumentationRegistry.getArguments().getString("additionalTestOutputDir")
                            ?.let(::File) ?: instrumentation.targetContext.cacheDir
                        output.mkdirs()
                        File(output, "fixed-tab-$count.png").outputStream().use {
                            image.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
                        }
                    }
                }
            }
        }
        compose.runOnIdle {
            selected.intValue = 0
            useLegacyApi.value = true
        }
        compose.onAllNodes(isSelectable()).assertCountEquals(2)
        compose.onNodeWithText(labels[0]).assertIsSelected()
        compose.onNodeWithText(labels[1]).performClick().assertIsSelected()
        compose.onNodeWithText(labels[0]).assertIsNotSelected()
    }
}
