package com.shopl.sdg.template.popup.center.input

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInput
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputField
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputState
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputStyle
import com.shopl.sdg.template.popup.center.SDGCenterPopup
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.popup.center.preview.SDGInputCenterPopupPreviewParameter
import com.shopl.sdg.template.popup.center.preview.SDGInputCenterPopupPreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Popup - Center Popup - Input
 *
 * 입력이 필요한
 *
 * 확인 버튼 한 개와 입력 라벨을 표시합니다.
 *
 * @version 2.3.4-
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19226-12213&m=dev">Figma</a>
 */
@Composable
fun SDGInputCenterPopup(
    title: String?,
    description: String?,
    confirmLabel: String,
    onClickConfirm: () -> Unit,
    inputLabel: String,
    inputContent: String,
    hint: String,
    state: SDGFixedTextInputState,
    onInputChange: (String) -> Unit,
    focusRequester: FocusRequester? = null,
    inputField: SDGFixedTextInputField = SDGFixedTextInputField.LightGray,
    inputMaxLength: Int = Int.MAX_VALUE,
    confirmLabelColor: Color = SDGColor.Neutral700,
    titleAlignment: TextAlign = TextAlign.Left,
    enabled: Boolean = true,
) {
    SDGInputCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.OneOption(
            label = confirmLabel,
            onClick = onClickConfirm,
            labelColor = confirmLabelColor,
            enabled = enabled
        ),
        title = title,
        description = description,
        showInputLabel = SDGInputCenterPopupShowInputLabel.True(inputLabel),
        inputContent = inputContent,
        hint = hint,
        state = state,
        onInputChange = onInputChange,
        focusRequester = focusRequester,
        inputField = inputField,
        inputMaxLength = inputMaxLength,
        titleAlignment = titleAlignment,
    )
}

/**
 * SDG - Popup - Center Popup - Input
 *
 * 입력이 필요한
 *
 * 본문은 null 또는 공백이면 숨깁니다. 입력 라벨은 [showInputLabel]로 표시 여부를 지정합니다.
 * 버튼 한 개는 [SDGCenterPopupButtonOption.OneOption], 두 개는 [SDGCenterPopupButtonOption.TwoOption]을 사용합니다.
 * 팝업 닫기 동작은 [SDGCenterPopup]의 버튼 옵션을 따릅니다.
 *
 * @version 2.3.4-
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19226-12213&m=dev">Figma</a>
 */
@Composable
fun SDGInputCenterPopup(
    buttonOption: SDGCenterPopupButtonOption,
    title: String?,
    description: String?,
    showInputLabel: SDGInputCenterPopupShowInputLabel,
    inputContent: String,
    hint: String,
    state: SDGFixedTextInputState,
    onInputChange: (String) -> Unit,
    focusRequester: FocusRequester? = null,
    inputField: SDGFixedTextInputField = SDGFixedTextInputField.LightGray,
    inputMaxLength: Int = Int.MAX_VALUE,
    titleAlignment: TextAlign = TextAlign.Left,
) {
    SDGCenterPopup(
        buttonOption = buttonOption,
        title = title,
        titleAlignment = titleAlignment,
    ) {
        description.takeIf { !it.isNullOrBlank() }?.let {
            Description(
                description = it,
            )
        }

        InputPopupBody(
            showInputLabel = showInputLabel,
            inputContent = inputContent,
            hint = hint,
            state = state,
            onInputChange = onInputChange,
            focusRequester = focusRequester,
            inputField = inputField,
            inputMaxLength = inputMaxLength,
        )
    }
}

/**
 * 신규 Input Center Popup API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "inputState 대신 state를 사용하는 SDGInputCenterPopup을 사용하세요.",
)
@Composable
fun SDGInputCenterPopup(
    title: String?,
    description: String?,
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
    val state = when (inputState) {
        InputState.Enable -> if (inputContent.isEmpty()) {
            SDGFixedTextInputState.Default
        } else {
            SDGFixedTextInputState.Completed
        }

        InputState.Disable -> SDGFixedTextInputState.Disabled
        is InputState.Error -> SDGFixedTextInputState.Error
    }
    val canChangeInput = inputState !is InputState.Error || enableOnError

    SDGInputCenterPopup(
        title = title,
        description = description,
        confirmLabel = confirmLabel,
        onClickConfirm = onClickConfirm,
        inputLabel = inputLabel,
        inputContent = inputContent,
        hint = hint,
        state = state,
        onInputChange = if (canChangeInput) onInputChange else { _ -> },
        focusRequester = focusRequester,
        inputField = if (inputBackgroundColor == SDGColor.Neutral50) {
            SDGFixedTextInputField.LightGray
        } else {
            SDGFixedTextInputField.White
        },
        inputMaxLength = inputMaxLength,
        confirmLabelColor = confirmLabelColor,
        titleAlignment = titleAlignment,
        enabled = enabled,
    )
}

@Composable
private fun Description(description: String) {
    SDGText(
        modifier = Modifier.fillMaxWidth(),
        text = description,
        typography = SDGTypography.Body1R,
        textColor = SDGColor.Neutral600
    )

    Spacer(modifier = Modifier.height(Spacing16))
}

@Composable
private fun InputPopupBody(
    showInputLabel: SDGInputCenterPopupShowInputLabel,
    inputContent: String,
    hint: String,
    state: SDGFixedTextInputState,
    onInputChange: (String) -> Unit,
    inputField: SDGFixedTextInputField,
    inputMaxLength: Int,
    focusRequester: FocusRequester? = null
) {
    if (showInputLabel is SDGInputCenterPopupShowInputLabel.True) {
        SDGText(
            modifier = Modifier.fillMaxWidth(),
            text = showInputLabel.text,
            typography = SDGTypography.Body1R,
            textColor = SDGColor.Neutral400
        )

        Spacer(modifier = Modifier.height(Spacing8))
    }

    SDGFixedTextInput(
        text = inputContent,
        placeholder = hint,
        state = state,
        inputField = inputField,
        style = SDGFixedTextInputStyle.Solid,
        onTextChange = onInputChange,
        focusRequester = focusRequester,
        maxLength = inputMaxLength,
    )
}

@Preview
@Composable
private fun PreviewSDGInputCenterPopup(
    @PreviewParameter(SDGInputCenterPopupPreviewParameterProvider::class)
    parameter: SDGInputCenterPopupPreviewParameter
) {
    var inputContent by remember(parameter) { mutableStateOf(parameter.initialInput) }

    SDGInputCenterPopup(
        buttonOption = parameter.buttonOption,
        title = parameter.title,
        description = parameter.description,
        showInputLabel = parameter.showInputLabel,
        inputContent = inputContent,
        hint = parameter.hint,
        state = parameter.state,
        onInputChange = { inputContent = it },
    )
}
