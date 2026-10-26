package com.shopl.sdg.mcp.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.shopl.sdg.component.button.box.SDGBoxButton
import com.shopl.sdg.component.button.box.SDGBoxButtonSize
import com.shopl.sdg.component.button.box.SDGBoxButtonType
import com.shopl.sdg.component.checkbox.SDGCheckBox
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxSelectedBackgroundColor
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxSize
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxState
import com.shopl.sdg_common.foundation.SDGColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet

/** 선택 집합의 갱신과 적용 처리는 서로 다른 이벤트로 전달합니다. */
@Composable
fun MultiChoiceExample(
    optionIds: PersistentList<String>,
    selectedIds: PersistentSet<String>,
    applyLabel: String,
    applyEnabled: Boolean,
    onToggle: (String) -> Unit,
    onApply: () -> Unit,
    label: @Composable (String) -> Unit,
) {
    Column {
        optionIds.forEach { id ->
            Row {
                SDGCheckBox(
                    state = if (id in selectedIds) SDGCheckBoxState.SELECTED else SDGCheckBoxState.DEFAULT,
                    selectedBackgroundColor = SDGCheckBoxSelectedBackgroundColor.NORMAL,
                    size = SDGCheckBoxSize.MEDIUM,
                    onClick = { onToggle(id) },
                )
                label(id)
            }
        }
        SDGBoxButton(
            size = SDGBoxButtonSize.Medium,
            type = SDGBoxButtonType.Solid,
            label = applyLabel,
            labelColor = SDGColor.Neutral0,
            backgroundColor = SDGColor.Primary300,
            enable = applyEnabled,
            onClick = onApply,
        )
    }
}
