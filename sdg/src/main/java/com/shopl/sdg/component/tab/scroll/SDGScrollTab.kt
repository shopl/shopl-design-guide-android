package com.shopl.sdg.component.tab.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList

/**
 * SDG - Tab - Scroll Tab
 *
 * 스와이프 동작으로 Tab 간 이동이 가능한 탭 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8213-5076&m=dev">Figma</a>
 */
@Composable
fun SDGScrollTab(
    titles: PersistentList<String>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
    marginValues: PaddingValues = PaddingValues(),
    backgroundColor: Color = SDGColor.Neutral0,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedTabIndex) {
        listState.animateScrollToItem(selectedTabIndex)
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(marginValues),
        state = listState
    ) {
        itemsIndexed(titles) { index, title ->
            SDGScrollTabItem(
                title = title,
                isSelected = index == selectedTabIndex,
                onClick = { onTabClick(index) }
            )
        }
    }
}

@Composable
private fun SDGScrollTabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SDGText(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            text = title,
            textColor = if (isSelected) SDGColor.Neutral700 else SDGColor.Neutral350,
            typography = SDGTypography.Title2SB
        )

        // Indicator / Underline
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(SDGColor.Neutral700)
            )
        } else {
            // Default underline (divider)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SDGColor.Neutral200)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGScrollTab(
    @PreviewParameter(SDGScrollTabPreviewParameterProvider::class)
    parameter: SDGScrollTabPreviewParameter
) {
    with(parameter) {
        SDGScrollTab(
            titles = titles,
            selectedTabIndex = selectedTabIndex,
            onTabClick = {},
            marginValues = marginValues
        )
    }
}