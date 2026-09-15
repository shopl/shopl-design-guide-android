package com.shopl.sdg.component.tab.icon

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

/** 상위 Icon Tab의 Selected Tab에서 결정되는 아이템의 내부 State입니다. */
internal enum class SDGIconTabItemState(
    internal val backgroundColor: Color,
    internal val borderColor: Color,
    internal val countColor: Color,
) {
    Selected(
        backgroundColor = SDGColor.Neutral600,
        borderColor = SDGColor.Neutral600,
        countColor = SDGColor.Neutral0,
    ),
    Unselected(
        backgroundColor = SDGColor.Neutral0,
        borderColor = SDGColor.Neutral200,
        countColor = SDGColor.Neutral500,
    ),
}
