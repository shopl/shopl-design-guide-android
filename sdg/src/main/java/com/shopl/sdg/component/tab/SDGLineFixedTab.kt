package com.shopl.sdg.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Component - Tab - Line Fixed Tab
 *
 * 페이지 내 유사한 콘텐츠를 그룹화하여 섹션 간 이동 시 사용하는 컴포넌트 - Line Fixed Tab
 *
 * @param tabTitles Tab Title 리스트
 * @param selectedTabIndex 현재 선택된 Tab의 index
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7460-24521&m=dev">Figma</a>
 */
@Composable
fun SDGLineFixedTab(
    modifier: Modifier = Modifier,
    tabTitles: List<String>,
    selectedTabIndex: Int,
    backgroundColor: Color = SDGColor.Neutral0,
    dividerColor: Color = SDGColor.Neutral100,
    indicatorColor: Color = SDGColor.Neutral700,
    onClick: (Int) -> Unit
) {
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        Indicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
            color = indicatorColor
        )
    }
    TabRow(
        modifier = modifier,
        containerColor = backgroundColor,
        selectedTabIndex = selectedTabIndex,
        indicator = indicator,
        divider = {
            HorizontalDivider(
                thickness = 1.5.dp,
                color = dividerColor
            )
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

@Composable
private fun Tab(
    text: String,
    onClick: () -> Unit,
    selected: Boolean,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier
            .padding(vertical = SDGSpacing.Spacing4)
            .height(32.dp)
            .background(color = backgroundColor)
            .clickable(hasRipple = false) {
                onClick()
            }
    ) {
        SDGText(
            modifier = Modifier.align(Alignment.TopCenter),
            text = text,
            textColor = if (selected) SDGColor.Neutral700 else SDGColor.Neutral350,
            typography = SDGTypography.Body1SB
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

@Composable
@Preview
private fun PreviewTwoOptions() {
    SDGLineFixedTab(
        tabTitles = listOf("Label", "Label"),
        selectedTabIndex = 0,
    ) {}
}

@Composable
@Preview
private fun PreviewThreeOptions() {
    SDGLineFixedTab(
        tabTitles = listOf("Label", "Label", "Label"),
        selectedTabIndex = 1,
        indicatorColor = SDGColor.Primary300,
        dividerColor = SDGColor.Red300
    ) {}
}