package com.shopl.sdg.component.tab.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

private val SDGIconTabHeight = 76.dp
private val SDGIconTabIcAreaSize = 20.dp

/**
 * SDG - Tab - Icon Tab Item
 *
 * 상위 Icon Tab의 Selected Tab으로 결정된 State를 그리는 내부 컴포넌트입니다.
 * Label, Count, Show Count, Icon Tab ic는 선택 여부와 관계없이 동일한 데이터를 사용합니다.
 * Count는 가용 너비에 맞춰 최대 수치 표기(예: 999+)로 가공한 문자열을 전달합니다.
 *
 * @see SDGTabItem
 */
@Composable
internal fun SDGIconTabItem(
    state: SDGIconTabItemState,
    label: String,
    count: String,
    showCount: Boolean,
    iconTabIc: SDGIconTabIcon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSelected = state == SDGIconTabItemState.Selected

    Column(
        modifier = modifier
            .height(SDGIconTabHeight)
            .clip(SDGCornerRadius.BoxRadius.Radius12)
            .background(state.backgroundColor)
            .border(
                width = 1.dp,
                color = state.borderColor,
                shape = SDGCornerRadius.BoxRadius.Radius12,
            )
            .semantics { selected = isSelected }
            .clickable(role = Role.Tab, onClick = onClick)
            .padding(horizontal = Spacing4),
        verticalArrangement = Arrangement.spacedBy(Spacing8, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isSelected) {
            SDGText(
                text = label,
                textColor = SDGColor.Neutral0,
                typography = SDGTypography.Body2R,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            Box(
                modifier = Modifier.size(SDGIconTabIcAreaSize),
                contentAlignment = Alignment.Center,
            ) {
                SDGImage(
                    resId = iconTabIc.icon,
                    color = iconTabIc.tint,
                    modifier = Modifier.size(iconTabIc.size.size),
                    contentDescription = label,
                )
            }
        }

        if (showCount) {
            SDGText(
                text = count,
                textColor = state.countColor,
                typography = SDGTypography.Body2SB,
                textAlign = TextAlign.Center,
                maxLines = 1,
                softWrap = false,
            )
        }
    }
}
