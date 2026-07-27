package com.shopl.sdg.template.popup.center.input

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
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
import com.shopl.sdg.template.popup.center.preview.SDGInputCenterPopupParameterProvider
import com.shopl.sdg.template.popup.center.preview.SDGInputCenterPopupPreviewBody
import com.shopl.sdg.template.popup.center.preview.SDGInputCenterPopupPreviewData
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
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
        description.takeIf { !it.isNullOrBlank() }?.let {
            Description(
                description = it,
            )
        }

        InputPopupBody(
            inputLabel = inputLabel,
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
        text = description,
        typography = SDGTypography.Body1R,
        textColor = SDGColor.Neutral600
    )

    Spacer(modifier = Modifier.height(Spacing16))
}

@Composable
private fun InputPopupBody(
    inputLabel: String,
    inputContent: String,
    hint: String,
    state: SDGFixedTextInputState,
    onInputChange: (String) -> Unit,
    inputField: SDGFixedTextInputField,
    inputMaxLength: Int,
    focusRequester: FocusRequester? = null
) {
    SDGText(
        text = inputLabel,
        typography = SDGTypography.Body1R,
        textColor = SDGColor.Neutral400
    )

    Spacer(modifier = Modifier.height(Spacing8))

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
    @PreviewParameter(SDGInputCenterPopupParameterProvider::class)
    data: SDGInputCenterPopupPreviewData
) {
    SDGInputCenterPopupPreviewBody(data = data)
}
