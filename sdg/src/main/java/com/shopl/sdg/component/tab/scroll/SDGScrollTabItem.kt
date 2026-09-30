package com.shopl.sdg.component.tab.scroll

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.shopl.sdg_common.ext.bottomBorder
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.ui.components.SDGText

/**
 * 한 줄 라벨과 선택 상태를 표시하고 클릭을 상위 Scroll Tab으로 전달합니다.
 * With Underline은 라벨 아래 6dp 영역에 선택 시 2dp, 미선택 시 1dp 인디케이터를 그립니다.
 * maxItemWidth를 지정하면 긴 라벨은 말줄임하며, 글꼴 배율이 커지면 높이도 함께 확장됩니다.
 */
@Composable
internal fun SDGScrollTabItem(
    style: SDGScrollTabStyle,
    size: SDGScrollTabSize,
    state: SDGScrollTabItemState,
    label: String,
    maxItemWidth: Dp?,
    onClick: () -> Unit,
) {
    SDGText(
        text = label,
        textColor = state.labelColor,
        typography = size.typography,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        softWrap = false,
        modifier = Modifier
            .then(if (maxItemWidth != null) Modifier.widthIn(max = maxItemWidth) else Modifier)
            .heightIn(min = size.height)
            .semantics { selected = state.isSelected }
            .clickable(role = Role.Tab, onClick = onClick)
            .then(
                if (style.showUnderline) {
                    Modifier.bottomBorder(strokeWidth = state.underlineThickness, color = state.underlineColor)
                } else {
                    Modifier
                }
            )
            .padding(bottom = Spacing6),
    )
}
