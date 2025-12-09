package com.shopl.sdg.component.tab.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.bottomBorder
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing20
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.launch

/**
 * SDG - Tab - Scroll Tab
 *
 * 제한없이 나열되는 탭 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8213-5076&m=dev">Figma</a>
 */
@Composable
fun SDGScrollTab(
    type: SDGScrollTabType,
    titles: PersistentList<String>,
    selectedIndex: Int?,
    onTabClick: (Int) -> Unit,
    isFillMaxWidth: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp),
    marginValues: PaddingValues = PaddingValues(0.dp),
    maxItemWidth: Dp? = null,
    backgroundColor: Color = SDGColor.Neutral0,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .then(if (isFillMaxWidth) Modifier.fillMaxWidth() else Modifier)
            .padding(marginValues)
            .background(backgroundColor)
    ) {
        LazyRow(
            contentPadding = contentPadding,
            state = listState
        ) {
            itemsIndexed(titles) { index, title ->
                Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                    SDGScrollTabItem(
                        type = type,
                        title = title,
                        isSelected = index == selectedIndex,
                        maxItemWidth = maxItemWidth,
                        onClick = {
                            onTabClick(index)
                            if (index == titles.lastIndex) {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index)
                                }
                            }
                        }
                    )

                    if (index < titles.lastIndex) {
                        SDGScrollTabSpacer(type = type)
                    }
                }
            }
        }
    }
}

@Composable
private fun SDGScrollTabItem(
    type: SDGScrollTabType,
    title: String,
    isSelected: Boolean,
    maxItemWidth: Dp?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .tabItemStyle(type, isSelected),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SDGText(
            text = title,
            textColor = if (isSelected) SDGColor.Neutral700 else SDGColor.Neutral350,
            typography = SDGTypography.Title2SB,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = if (maxItemWidth != null) Modifier.widthIn(max = maxItemWidth) else Modifier
        )
    }
}

@Composable
private fun SDGScrollTabSpacer(type: SDGScrollTabType) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(Spacing20)
            .tabSpacerStyle(type)
    )
}

/**
 * 탭 아이템의 테두리와 패딩 스타일을 처리합니다.
 */
private fun Modifier.tabItemStyle(
    type: SDGScrollTabType,
    isSelected: Boolean
): Modifier {
    if (type != SDGScrollTabType.Line) return this

    return this
        .bottomBorder(
            strokeWidth = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) SDGColor.Neutral700 else SDGColor.Neutral200
        )
        .padding(bottom = Spacing6)
}

/**
 * 탭 스페이서의 스타일을 처리합니다.
 */
private fun Modifier.tabSpacerStyle(
    type: SDGScrollTabType
): Modifier {
    if (type != SDGScrollTabType.Line) return this

    return this
        .bottomBorder(
            strokeWidth = 1.dp,
            color = SDGColor.Neutral200
        )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGScrollTab(
    @PreviewParameter(SDGScrollTabPreviewParameterProvider::class)
    parameter: SDGScrollTabPreviewParameter
) {
    with(parameter) {
        SDGScrollTab(
            type = type,
            titles = titles,
            selectedIndex = selectedIndex,
            onTabClick = {},
            maxItemWidth = maxItemWidth,
            contentPadding = contentPadding,
        )
    }
}
