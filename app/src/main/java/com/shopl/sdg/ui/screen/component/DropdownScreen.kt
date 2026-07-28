package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.dropdown.SDGDropdown
import com.shopl.sdg.component.dropdown.SDGDropdownInputField
import com.shopl.sdg.component.dropdown.SDGDropdownState
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Dropdown
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27309-517&m=dev">Figma</a>
 */
@Composable
internal fun DropdownScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    val states = persistentListOf<SDGSampleBaseTabItem<SDGDropdownState>>(
        SDGSampleBaseTabItem(
            title = SDGDropdownState.Default.toString(),
            item = SDGDropdownState.Default,
        ),
        SDGSampleBaseTabItem(
            title = SDGDropdownState.Selected.toString(),
            item = SDGDropdownState.Selected,
        ),
        SDGSampleBaseTabItem(
            title = SDGDropdownState.Error.toString(),
            item = SDGDropdownState.Error,
        ),
    )
    val inputFields = persistentListOf<SDGSampleBaseTabItem<SDGDropdownInputField>>(
        SDGSampleBaseTabItem(
            title = SDGDropdownInputField.White.fieldName,
            item = SDGDropdownInputField.White,
        ),
        SDGSampleBaseTabItem(
            title = SDGDropdownInputField.LightGray.fieldName,
            item = SDGDropdownInputField.LightGray,
        ),
    )

    SDGSampleBaseComponentScaffold(
        componentName = ComponentScene.Dropdown.displayLabel,
        componentDescription = "여러 옵션 중 하나를 선택하는 드롭다운 컴포넌트",
        types = states,
        specs = inputFields,
        componentContent = { state, inputField, status ->
            if (state != null && inputField != null) {
                DropdownContent(
                    state = if (status == SDGSampleStatus.DISABLED) {
                        SDGDropdownState.Disabled
                    } else {
                        state
                    },
                    inputField = inputField,
                )
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun DropdownContent(
    state: SDGDropdownState,
    inputField: SDGDropdownInputField,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SDGSpacing.Spacing16,
                vertical = SDGSpacing.Spacing40,
            ),
        contentAlignment = Alignment.Center,
    ) {
        SDGDropdown(
            text = when (state) {
                SDGDropdownState.Default -> ""
                SDGDropdownState.Selected,
                SDGDropdownState.Disabled,
                SDGDropdownState.Error -> "Selected Text"
            },
            state = state,
            placeholder = "Placeholder",
            inputField = inputField,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun PreviewDropdownScreen() {
    ShoplDesignGuideTheme {
        DropdownScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
