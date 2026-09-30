package com.shopl.sdg.component.tab.scroll

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing18
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing20
import com.shopl.sdg_common.foundation.typography.SDGTypography

/** Scroll Tab의 기본 높이, 라벨 타이포그래피와 아이템 사이 간격입니다. */
enum class SDGScrollTabSize(
    internal val height: Dp,
    internal val typography: SDGTypography,
    internal val itemSpacing: Dp,
) {
    Large(height = 28.dp, typography = SDGTypography.Title2SB, itemSpacing = Spacing20),
    Medium(height = 26.dp, typography = SDGTypography.Body1SB, itemSpacing = Spacing18),
}
