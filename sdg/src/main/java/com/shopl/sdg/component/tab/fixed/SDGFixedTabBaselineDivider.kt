package com.shopl.sdg.component.tab.fixed

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

/** Fixed Tab의 좌우 Baseline Divider 노출 상태입니다. */
@Immutable
sealed interface SDGFixedTabBaselineDivider {
    /** 탭 내부 밑줄만 표시하고 좌우로 확장하지 않습니다. */
    data object Hidden : SDGFixedTabBaselineDivider

    /**
     * 탭 셀의 좌우 여백까지 Baseline Divider를 표시합니다.
     *
     * @param horizontalPadding 탭 셀의 좌우 여백이자 Divider의 각 방향 확장 폭
     */
    data class Visible(
        val horizontalPadding: Dp,
    ) : SDGFixedTabBaselineDivider
}
