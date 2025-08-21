package com.shopl.sdg.template.popup.center.input

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInput
import com.shopl.sdg.template.popup.center.SDGCenterPopup
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg_common.enums.OutlineType
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Popup - Center Popup - Input
 *
 * 입력이 필요한 [CenterPopup]
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19226-12432&t=dilCeVYD7pIRNnAx-4">Figma</a>
 */
@Composable
fun SDGInputCenterPopup(
    title: String?,
    description: String,
    confirmLabel: String,
    onClickConfirm: () -> Unit,
    inputLabel: String,
    inputContent: String,
    hint: String,
    inputState: InputState,
    onInputChange: (String) -> Unit,
    focusRequester: FocusRequester? = null,
    inputBackgroundColor: Color = SDGColor.Neutral50,
    inputMaxLength: Int = Int.MAX_VALUE,
    enableOnError: Boolean = false,
    confirmLabelColor: Color = SDGColor.Neutral700,
    titleAlignment: TextAlign = TextAlign.Left,
    enabled: Boolean = true,
) {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.OneOption(
            label = confirmLabel,
            onClick = onClickConfirm,
            labelColor = confirmLabelColor,
            enabled = enabled
        ),
        title = title,
        titleAlignment = titleAlignment,
    ) {
        InputPopupBody(
            description = description,
            inputLabel = inputLabel,
            inputContent = inputContent,
            hint = hint,
            inputState = inputState,
            onInputChange = onInputChange,
            focusRequester = focusRequester,
            inputBackgroundColor = inputBackgroundColor,
            inputMaxLength = inputMaxLength,
            enableOnError = enableOnError
        )
    }
}

@Composable
private fun InputPopupBody(
    description: String,
    inputLabel: String,
    inputContent: String,
    hint: String,
    inputState: InputState,
    onInputChange: (String) -> Unit,
    inputBackgroundColor: Color,
    inputMaxLength: Int,
    enableOnError: Boolean,
    focusRequester: FocusRequester? = null
) {
    SDGText(
        text = description,
        typography = SDGTypography.Body1R,
        textColor = SDGColor.Neutral600
    )

    Spacer(modifier = Modifier.height(16.dp))

    SDGText(
        text = inputLabel,
        typography = SDGTypography.Body1R,
        textColor = SDGColor.Neutral400
    )

    Spacer(modifier = Modifier.height(8.dp))

    SDGFixedTextInput(
        outlineType = OutlineType.BASIC,
        input = inputContent,
        hint = hint,
        inputState = inputState,
        onInputChange = onInputChange,
        focusRequester = focusRequester,
        backgroundColor = inputBackgroundColor,
        maxLength = inputMaxLength,
        enableOnError = enableOnError
    )
}

@Preview
@Composable
private fun PreviewSDGInputCenterPopup() {
    var inputContent by remember { mutableStateOf("") }

    SDGInputCenterPopup(
        title = "Title",
        description = "Description",
        confirmLabel = "확인",
        onClickConfirm = {},
        inputLabel = "Input Label",
        inputContent = inputContent,
        hint = "힌트 문구",
        inputState = InputState.Enable,
        onInputChange = { inputContent = it },
        focusRequester = null,
        inputBackgroundColor = SDGColor.Neutral50,
        inputMaxLength = Int.MAX_VALUE,
        enableOnError = false,
        confirmLabelColor = SDGColor.Neutral700,
        titleAlignment = TextAlign.Left,
        enabled = true
    )
}
