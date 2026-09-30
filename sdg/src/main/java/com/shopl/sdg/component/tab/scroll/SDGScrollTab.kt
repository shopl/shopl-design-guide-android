package com.shopl.sdg.component.tab.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.tab.scroll.preview.SDGScrollTabPreviewParameter
import com.shopl.sdg.component.tab.scroll.preview.SDGScrollTabPreviewParameterProvider
import com.shopl.sdg_common.ext.bottomBorder
import com.shopl.sdg_common.ext.orDefault
import com.shopl.sdg_common.foundation.SDGColor
import kotlinx.collections.immutable.PersistentList

/**
 * SDG - Tab - Scroll Tab
 *
 * 수평 스크롤로 개수 제한 없이 카테고리를 탐색하고 라벨과 인디케이터로 선택 상태를 표시합니다.
 *
 * @version 2.3.48
 *
 * @param style Style: WithUnderline / OnlyText
 * @param size Size: Large / Medium
 * @param titles 선택 위치와 관계없이 유지되는 각 탭의 한 줄 라벨
 * @param selectedTab Selected Tab: 0부터 시작하는 필수 선택 인덱스. 0 이상 탭 개수 미만으로 지정합니다.
 * @param onTabClick 클릭한 탭의 인덱스를 전달합니다. 호출부에서 selectedTab을 갱신합니다.
 * @param isFillMaxWidth 부모의 가용 너비를 채웁니다. WithUnderline이면 좌우 끝까지 기준선을 표시합니다.
 * @param contentPadding 스크롤 콘텐츠의 내부 여백. 좌우 여백은 스크롤 시작과 끝에 적용됩니다.
 * @param marginValues 컴포넌트 외부 여백
 * @param maxItemWidth 라벨의 최대 너비. 초과한 텍스트는 말줄임하며 null이면 라벨 길이만큼 확장됩니다.
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8213-5076&m=dev">Figma</a>
 */
@Composable
fun SDGScrollTab(
    style: SDGScrollTabStyle,
    size: SDGScrollTabSize,
    titles: PersistentList<String>,
    selectedTab: Int,
    onTabClick: (Int) -> Unit,
    isFillMaxWidth: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp),
    marginValues: PaddingValues = PaddingValues(0.dp),
    maxItemWidth: Dp? = null,
    backgroundColor: Color = SDGColor.Neutral0,
) {
    require(selectedTab in titles.indices) {
        "Selected Tab은 0부터 ${titles.lastIndex} 사이의 인덱스여야 합니다. (입력값: $selectedTab)"
    }

    val listState = rememberLazyListState()

    LaunchedEffect(selectedTab, titles, size, maxItemWidth, contentPadding) {
        val layoutInfo = listState.layoutInfo
        val visibleItems = layoutInfo.visibleItemsInfo
        val itemInfo = visibleItems.find { it.index == selectedTab }

        if (itemInfo == null) {
            listState.animateScrollToItem(selectedTab)
        } else {
            val viewportStart = layoutInfo.viewportStartOffset
            val viewportEnd = layoutInfo.viewportEndOffset
            val isPartiallyHidden = itemInfo.offset < viewportStart ||
                (itemInfo.offset + itemInfo.size) > viewportEnd

            if (isPartiallyHidden) {
                val offset = (viewportStart + viewportEnd - itemInfo.size) / 2
                listState.animateScrollToItem(selectedTab, -offset)
            }
        }
    }

    Box(
        modifier = Modifier
            .then(if (isFillMaxWidth) Modifier.fillMaxWidth() else Modifier)
            .padding(marginValues)
            .background(backgroundColor)
    ) {
        if (isFillMaxWidth && style.showUnderline) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = contentPadding.calculateBottomPadding())
                    .height(height = 1.dp)
                    .align(Alignment.BottomStart)
                    .background(SDGColor.Neutral200)
            )
        }

        LazyRow(
            modifier = Modifier.selectableGroup(),
            contentPadding = contentPadding,
            state = listState,
        ) {
            itemsIndexed(
                items = titles,
                key = { index, _ -> index },
            ) { index, title ->
                Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                    SDGScrollTabItem(
                        style = style,
                        size = size,
                        state = if (index == selectedTab) {
                            SDGScrollTabItemState.Selected
                        } else {
                            SDGScrollTabItemState.Unselected
                        },
                        label = title,
                        maxItemWidth = maxItemWidth,
                        onClick = { onTabClick(index) }
                    )

                    if (index < titles.lastIndex) {
                        SDGScrollTabSpacer(style = style, size = size)
                    }
                }
            }
        }
    }
}

/**
 * 크기별 아이템 간격을 유지하며 With Underline에서는 1dp 구분선으로 인디케이터를 연결합니다.
 */
@Composable
private fun SDGScrollTabSpacer(style: SDGScrollTabStyle, size: SDGScrollTabSize) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(size.itemSpacing)
            .then(
                if (style.showUnderline) {
                    Modifier.bottomBorder(strokeWidth = 1.dp, color = SDGColor.Neutral200)
                } else {
                    Modifier
                }
            )
    )
}

/**
 * 기존 Type과 선택 인덱스를 v2.3.48 API로 연결하며 Large 크기를 사용합니다.
 * selectedIndex가 null이면 첫 번째 탭(0)을 선택하며, 범위 밖 인덱스는 새 API와 동일하게 검증합니다.
 */
@Deprecated("style, size, selectedTab을 사용하는 SDGScrollTab으로 전환하세요.")
@Suppress("DEPRECATION")
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
    SDGScrollTab(
        style = when (type) {
            SDGScrollTabType.Line -> SDGScrollTabStyle.WithUnderline
            SDGScrollTabType.Text -> SDGScrollTabStyle.OnlyText
        },
        size = SDGScrollTabSize.Large,
        titles = titles,
        selectedTab = selectedIndex.orDefault(),
        onTabClick = onTabClick,
        isFillMaxWidth = isFillMaxWidth,
        contentPadding = contentPadding,
        marginValues = marginValues,
        maxItemWidth = maxItemWidth,
        backgroundColor = backgroundColor,
    )
}

/** 크기·스타일·선택 위치와 여백 조합을 확인하는 미리보기입니다. */
@Preview(showBackground = true, widthDp = 360)
@Composable
private fun PreviewSDGScrollTab(
    @PreviewParameter(SDGScrollTabPreviewParameterProvider::class)
    parameter: SDGScrollTabPreviewParameter
) {
    with(parameter) {
        SDGScrollTab(
            style = style,
            size = size,
            titles = titles,
            selectedTab = selectedTab,
            onTabClick = {},
            isFillMaxWidth = isFillMaxWidth,
            contentPadding = contentPadding,
            marginValues = marginValues,
            maxItemWidth = maxItemWidth,
            backgroundColor = backgroundColor,
        )
    }
}
