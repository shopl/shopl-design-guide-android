package com.shopl.sdg.component.tab.fixed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Tab - Fixed Tab
 *
 * 영역 내에서 3개 이하로 나열되는 텝 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=20900-15091&m=dev">Figma</a>
 */
@Composable
fun SDGFixedTab(
    type: SDGFixedTabType,
    onTabClick: (Int) -> Unit,
    selectedTabIndex: Int = 0,
) {
    val tabTitleList by remember(type) {
        mutableStateOf(
            when(type) {
                is SDGFixedTabType.TwoOption -> listOf(type.firstTitle, type.secondTitle)
                is SDGFixedTabType.ThreeOption -> listOf(type.firstTitle, type.secondTitle, type.thirdTitle)
            }
        )
    }
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
            height = 2.dp,
            color = SDGColor.Neutral700
        )
    }
    TabRow(
        modifier = Modifier.fillMaxWidth(),
        containerColor = SDGColor.Transparent,
        selectedTabIndex = selectedTabIndex,
        indicator = indicator,
    ) {
        tabTitleList.forEachIndexed { index, title ->
            Tab(
                tabLabel = title,
                onClick = {
                    onTabClick(index)
                },
                selected = (index == selectedTabIndex),
            )
        }
    }
}

@Composable
private fun Tab(
    tabLabel: String,
    onClick: () -> Unit,
    selected: Boolean,
) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .clickable {
                onClick()
            }
    ) {
        SDGText(
            modifier = Modifier.align(Alignment.TopCenter),
            text = tabLabel,
            textColor = if (selected) SDGColor.Neutral700 else SDGColor.Neutral350,
            typography = SDGTypography.Body1SB
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGFixedTab(
    @PreviewParameter(SDGFixedTabPreviewParameterProvider::class)
    parameter: SDGFixedTabPreviewParameter
) {
    with(parameter) {
        SDGFixedTab(
            type = type,
            onTabClick = onTabClick,
            selectedTabIndex = selectedTabIndex,
        )
    }
}