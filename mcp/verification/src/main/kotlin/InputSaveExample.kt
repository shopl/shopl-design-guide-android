package com.shopl.sdg.mcp.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.shopl.sdg.component.button.box.SDGBoxButton
import com.shopl.sdg.component.button.box.SDGBoxButtonSize
import com.shopl.sdg.component.button.box.SDGBoxButtonType
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInput
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputField
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputState
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputStyle
import com.shopl.sdg_common.foundation.SDGColor

/** 입력 상태와 저장 조건은 호출 화면에서 계산합니다. 문구는 앱의 리소스를 연결합니다. */
@Composable
fun InputSaveExample(
    value: String,
    placeholder: String,
    inputState: SDGSimpleTextInputState,
    saveLabel: String,
    saveEnabled: Boolean,
    onValueChange: (String) -> Unit,
    onSave: () -> Unit,
) {
    Column {
        SDGSimpleTextInput(
            text = value,
            placeholder = placeholder,
            state = inputState,
            inputField = SDGSimpleTextInputField.LightGray,
            style = SDGSimpleTextInputStyle.Solid,
            onTextChange = onValueChange,
        )
        SDGBoxButton(
            size = SDGBoxButtonSize.Medium,
            type = SDGBoxButtonType.Solid,
            label = saveLabel,
            labelColor = SDGColor.Neutral0,
            backgroundColor = SDGColor.Primary300,
            enable = saveEnabled,
            onClick = onSave,
        )
    }
}
