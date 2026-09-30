package com.shopl.sdg.component.tab.scroll

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor

/** 상위 Scroll Tab의 Selected Tab에서 결정되는 아이템의 내부 State입니다. */
internal enum class SDGScrollTabItemState(
    internal val labelColor: Color,
    internal val underlineColor: Color,
    internal val underlineThickness: Dp,
) {
    Selected(
        labelColor = SDGColor.Neutral700,
        underlineColor = SDGColor.Neutral700,
        underlineThickness = 2.dp,
    ),
    Unselected(
        labelColor = SDGColor.Neutral350,
        underlineColor = SDGColor.Neutral200,
        underlineThickness = 1.dp,
    ),
    ;

    internal val isSelected: Boolean get() = this == Selected
}
