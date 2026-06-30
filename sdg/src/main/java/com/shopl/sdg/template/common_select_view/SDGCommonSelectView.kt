package com.shopl.sdg.template.common_select_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.component.empty_icon.SDGBasicEmpty
import com.shopl.sdg.component.navigation.basic.SDGBasicNavi
import com.shopl.sdg.component.navigation.basic.SDGBasicNaviIconItem
import com.shopl.sdg.component.search_bar.box.SDGBoxSearch
import com.shopl.sdg.component.search_bar.box.SDGBoxSearchType
import com.shopl.sdg.template.common_select_view.preview.SDGCommonSelectViewPreviewParameterProvider
import com.shopl.sdg.template.common_select_view.preview.SDGCommonSelectViewPreviewParams
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG - Select View - Common Select View
 *
 * 특정 항목 선택을 위한 리스트를 제공하는 풀스크린 모달
 *
 * @version 2.3.30
 *
 * @see
 * 네비게이션, 검색, 선택 아이템 바디로 구성된 일반 Select View.
 * 가운데 아이템 영역 전체는 [content] 슬롯으로 외부에서 주입한다.
 *
 * @param navigationTitle 네비게이션 타이틀
 * @param searchInput 검색어 입력값
 * @param searchHint 검색어 힌트
 * @param state 상태(기본 / 데이터 없음)
 * @param onChangeSearchInput 검색어 입력 변경 콜백
 * @param onClickSearchDelete 검색어 삭제 클릭 콜백
 * @param onClickClose 닫기 클릭 콜백
 * @param content Select View 중앙 아이템 영역 슬롯
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=26057-1592&m=dev">Figma</a>
 */
@Composable
fun SDGCommonSelectView(
    navigationTitle: String,
    searchInput: String,
    searchHint: String,
    state: SDGCommonSelectViewState = SDGCommonSelectViewState.Default,
    onChangeSearchInput: (searchInput: String) -> Unit,
    onClickSearchDelete: () -> Unit,
    onClickClose: () -> Unit,
    content: @Composable BoxScope.() -> Unit = {},
) {
    CommonSelectViewScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SDGBasicNavi(
                title = navigationTitle,
                backgroundColor = SDGColor.Neutral100,
                leftIcon = null,
                rightIcons = persistentListOf(
                    SDGBasicNaviIconItem(
                        resId = R.drawable.ic_navi_close,
                        onClick = onClickClose,
                        color = SDGColor.Neutral700,
                    ),
                ),
            )
        },
        backgroundColor = SDGColor.Neutral100,
    ) {
        Column(
            modifier = Modifier
                .padding(top = SDGSpacing.Spacing10)
                .fillMaxSize()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = SDGCornerRadius.Radius20,
                        topEnd = SDGCornerRadius.Radius20,
                    )
                )
                .background(color = SDGColor.Neutral0)
        ) {
            SDGBoxSearch(
                type = SDGBoxSearchType.Line,
                input = searchInput,
                hint = searchHint,
                enabled = true,
                onInputChange = onChangeSearchInput,
                onDeleteClick = onClickSearchDelete,
                marginValues = PaddingValues(
                    start = SDGSpacing.Spacing20,
                    top = SDGSpacing.Spacing20,
                    end = SDGSpacing.Spacing20,
                    bottom = SDGSpacing.Spacing12,
                ),
                useStartRequester = false,
            )

            when (state) {
                SDGCommonSelectViewState.Default -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(weight = 1f),
                        content = content,
                    )
                }

                is SDGCommonSelectViewState.NoResult -> {
                    if (state.descriptionResId != null) {
                        SDGBasicEmpty(
                            iconResId = state.iconResId,
                            descResId = state.descriptionResId,
                            marginTop = SDGSpacing.Spacing60
                        )
                    } else {
                        SDGBasicEmpty(
                            iconResId = state.iconResId,
                            description = state.description,
                            marginTop = SDGSpacing.Spacing60
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CommonSelectViewScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    backgroundColor: Color = SDGColor.Neutral0,
    content: @Composable BoxScope.() -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                topBar()
            }
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(insets = WindowInsets.navigationBars),
        containerColor = backgroundColor,
        content = { innerPadding ->
            Box(
                content = content,
                modifier = Modifier.padding(paddingValues = innerPadding),
            )
        }
    )
}

@Preview(showBackground = true, locale = "ko")
@Composable
private fun PreviewSDGCommonSelectView(
    @PreviewParameter(SDGCommonSelectViewPreviewParameterProvider::class)
    params: SDGCommonSelectViewPreviewParams,
) {
    var searchInput by remember { mutableStateOf(params.searchInput) }

    SDGCommonSelectView(
        navigationTitle = params.title,
        searchInput = searchInput,
        searchHint = "검색",
        state = params.state,
        onChangeSearchInput = { changedSearchInput ->
            searchInput = changedSearchInput
        },
        onClickSearchDelete = {
            searchInput = ""
        },
        onClickClose = {},
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing1),
            contentPadding = PaddingValues(
                top = SDGSpacing.Spacing8,
                bottom = SDGSpacing.Spacing40,
            ),
        ) {
            items(
                items = params.items,
                key = { item -> item },
            ) { item ->
                SDGText(
                    text = item,
                    typography = SDGTypography.Body1R,
                    textColor = SDGColor.Neutral700,
                )
            }
        }
    }
}
