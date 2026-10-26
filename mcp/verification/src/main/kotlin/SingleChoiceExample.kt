package com.shopl.sdg.mcp.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.shopl.sdg.component.radio.SDGRadio
import com.shopl.sdg.component.radio.model.SDGRadioSelectedBackgroundColor
import com.shopl.sdg.component.radio.model.SDGRadioSize
import com.shopl.sdg.component.radio.model.SDGRadioState
import kotlinx.collections.immutable.PersistentList

/** ID는 항목별로 고유해야 합니다. 라벨과 선택 상태는 호출 화면이 제공합니다. */
@Composable
fun SingleChoiceExample(
    optionIds: PersistentList<String>,
    selectedId: String?,
    onSelect: (String) -> Unit,
    label: @Composable (String) -> Unit,
) {
    Column {
        optionIds.forEach { id ->
            Row {
                SDGRadio(
                    state = if (id == selectedId) SDGRadioState.SELECTED else SDGRadioState.DEFAULT,
                    selectedBackgroundColor = SDGRadioSelectedBackgroundColor.NORMAL,
                    size = SDGRadioSize.MEDIUM,
                    onClick = { onSelect(id) },
                )
                label(id)
            }
        }
    }
}
