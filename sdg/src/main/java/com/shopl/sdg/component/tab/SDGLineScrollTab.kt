package com.shopl.sdg.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface

@Stable
sealed class SDGLineScrollTabType {
    data class Line(
        val dividerColor: Color = SDGColor.Neutral100,
        val indicatorColor: Color = SDGColor.Neutral700
    ) : SDGLineScrollTabType()

    data object Text : SDGLineScrollTabType()
}

/**
 * tab 최소 크기 제약으로 사용 어려울듯..
 * ScrollableTabRowMinimumTabWidth
 */
@Composable
fun SDGLineScrollTab(
    isFillMaxWidth: Boolean = false,
    edgePadding: Dp = 0.dp,
    tabTitles: List<String>,
    selectedTabIndex: Int,
    type: SDGLineScrollTabType,
    backgroundColor: Color = SDGColor.Neutral0,
    dividerColor: Color = SDGColor.Neutral100,
    indicatorColor: Color = SDGColor.Neutral700,
    onClick: (Int) -> Unit
) {
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        when (type) {
            is SDGLineScrollTabType.Line -> {
                Indicator(
                    height = 2.dp,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = indicatorColor
                )
            }

            SDGLineScrollTabType.Text -> {}
        }
    }
    Box(
        modifier = Modifier.then(
            if (isFillMaxWidth) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
            }
        )
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            thickness = 1.dp,
            color = dividerColor
        )

        ScrollableTabRow(
            modifier = Modifier,
            containerColor = backgroundColor,
            selectedTabIndex = selectedTabIndex,
            indicator = indicator,
            edgePadding = edgePadding,
            divider = {
                when (type) {
                    is SDGLineScrollTabType.Line -> {
                        HorizontalDivider(
                            color = dividerColor
                        )
                    }

                    SDGLineScrollTabType.Text -> {}
                }
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    text = title,
                    onClick = {
                        onClick(index)
                    },
                    selected = (index == selectedTabIndex),
                    backgroundColor = backgroundColor
                )
            }
        }
    }
}

@Composable
private fun Tab(
    text: String,
    onClick: () -> Unit,
    selected: Boolean,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier
            .padding(bottom = 6.dp, start = 10.dp, end = 10.dp)
            .background(color = backgroundColor)
            .clickable(hasRipple = false) {
                onClick()
            }
    ) {
        IOText(
            modifier = Modifier.align(Alignment.TopCenter),
            text = text,
            textColor = if (selected) SDGColor.Neutral700 else SDGColor.Neutral350,
            fontSize = 18.sp,
            typeface = IOTypeface.SEMI_BOLD,
            lineHeight = 22.sp,
        )
    }
}

@Composable
private fun Indicator(
    modifier: Modifier = Modifier,
    height: Dp = 3.dp,
    color: Color = SDGColor.Neutral700
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .background(color = color)
    )
}