package com.shopl.sdg.component.tab.fixed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

private val SelectedTabUnderlineHeight = 2.dp
private val UnselectedTabUnderlineHeight = 1.dp

/**
 * SDG - Tab - Fixed Tab
 *
 * 고정된 영역 내에서 3개 이하로 분할되며, 페이지 내 유사한 콘텐츠를 그룹화하여 섹션 간 이동 시 사용하는 탭 컴포넌트
 *
 * @version 2.3.48
 *
 * @param option Option: 2 Option / 3 Option에 맞는 탭 라벨
 * @param selectedTab Selected Tab: 0부터 시작하는 필수 선택 인덱스. 0 이상 탭 개수 미만으로 지정합니다.
 * @param onTabClick 클릭한 탭의 인덱스를 전달합니다. 호출부에서 selectedTab을 갱신합니다.
 * @param unselectedTabUnderLineColor 미선택 탭의 밑줄과 Baseline Divider에 함께 적용할 색상
 * @param tabHorizontalPadding 탭 셀 바깥의 좌우 여백. 해당 영역까지 1dp Baseline Divider가 확장됩니다.
 * 기본값 0.dp에서는 탭 셀 바깥으로 확장하지 않습니다.
 * @param marginValues 컴포넌트 외부 여백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=20900-15091&m=dev">Figma</a>
 */
@Composable
fun SDGFixedTab(
    option: SDGFixedTabOption,
    selectedTab: Int,
    onTabClick: (Int) -> Unit,
    unselectedTabUnderLineColor: Color,
    tabHorizontalPadding: Dp = 0.dp,
    marginValues: PaddingValues = PaddingValues(),
) {
    require(selectedTab in option.tabs.indices) {
        "Selected Tab은 0부터 ${option.tabs.lastIndex} 사이의 인덱스여야 합니다. (입력값: $selectedTab)"
    }
    val selectedTabUnderline = @Composable { tabPositions: List<TabPosition> ->
        SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            height = SelectedTabUnderlineHeight,
            color = SDGColor.Neutral700
        )
    }
    Box(modifier = Modifier.fillMaxWidth().padding(marginValues)) {
        SecondaryIndicator(
            modifier = Modifier.align(Alignment.BottomCenter),
            height = UnselectedTabUnderlineHeight,
            color = unselectedTabUnderLineColor
        )
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = tabHorizontalPadding),
            containerColor = SDGColor.Transparent,
            selectedTabIndex = selectedTab,
            indicator = selectedTabUnderline,
            divider = {},
        ) {
            option.tabs.forEachIndexed { index, title ->
                Tab(
                    tabLabel = title,
                    onClick = { onTabClick(index) },
                    selected = (index == selectedTab),
                )
            }
        }
    }
}

/** 기존 Type과 선택 인덱스를 Option 기반 API로 연결합니다. */
@Deprecated("option, selectedTab을 사용하는 SDGFixedTab으로 전환하세요.")
@Suppress("DEPRECATION")
@Composable
fun SDGFixedTab(
    type: SDGFixedTabType,
    onTabClick: (Int) -> Unit,
    unselectedTabUnderLineColor: Color,
    selectedTabIndex: Int = 0,
) {
    val option = remember(type) {
        when (type) {
            is SDGFixedTabType.TwoOption -> SDGFixedTabOption.TwoOption(listOf(type.firstTitle, type.secondTitle))
            is SDGFixedTabType.ThreeOption -> SDGFixedTabOption.ThreeOption(
                listOf(type.firstTitle, type.secondTitle, type.thirdTitle)
            )
        }
    }
    SDGFixedTab(
        option = option,
        selectedTab = selectedTabIndex,
        onTabClick = onTabClick,
        unselectedTabUnderLineColor = unselectedTabUnderLineColor,
    )
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
            .semantics { this.selected = selected }
            .clickable(role = Role.Tab, onClick = onClick)
    ) {
        SDGText(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(horizontal = Spacing8),
            text = tabLabel,
            textColor = if (selected) SDGColor.Neutral700 else SDGColor.Neutral350,
            typography = SDGTypography.Body1SB,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, widthDp = 412)
@Composable
private fun PreviewSDGFixedTab(
    @PreviewParameter(SDGFixedTabPreviewParameterProvider::class)
    parameter: SDGFixedTabPreviewParameter
) {
    with(parameter) {
        Box(modifier = Modifier.width(width)) {
            SDGFixedTab(
                option = option,
                selectedTab = selectedTab,
                onTabClick = onTabClick,
                unselectedTabUnderLineColor = unselectedTabUnderLineColor,
                tabHorizontalPadding = tabHorizontalPadding,
            )
        }
    }
}
