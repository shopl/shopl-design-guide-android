package com.shopl.sdg.component.tab.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.shopl.sdg_common.ext.bottomBorder
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing20
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList

/**
 * SDG - Tab - Scroll Tab
 *
 * 제한없이 나열되는 탭 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8213-5076&m=dev">Figma</a>
 */
@Composable
fun SDGScrollTab(
    titles: PersistentList<String>,
    selectedTabIndex: Int,
    contentPadding: PaddingValues,
    onTabClick: (Int) -> Unit,
    marginValues: PaddingValues = PaddingValues(),
    backgroundColor: Color = SDGColor.Neutral0,
) {
    val listState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth()
            .background(backgroundColor),
        contentPadding = contentPadding,
        horizontalArrangement = spacedBy(Spacing20),
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

    LaunchedEffect(selectedTabIndex) {
        listState.animateScrollToItem(selectedTabIndex)
    }
}

@Composable
private fun SDGScrollTabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .bottomBorder(
                strokeWidth = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) SDGColor.GreenG else SDGColor.Red300
            )
            .padding(bottom = Spacing6),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SDGText(
            text = title,
            textColor = if (isSelected) SDGColor.Neutral700 else SDGColor.Neutral350,
            typography = SDGTypography.Title2SB
        )
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
            contentPadding = contentPadding,
            marginValues = marginValues
        )
    }
}