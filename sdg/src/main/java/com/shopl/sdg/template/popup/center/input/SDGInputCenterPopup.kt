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
import com.shopl.sdg.template.popup.center.SDGCenterPopup
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.popup.center.preview.SDGInputCenterPopupParameterProvider
import com.shopl.sdg.template.popup.center.preview.SDGInputCenterPopupPreviewBody
import com.shopl.sdg.template.popup.center.preview.SDGInputCenterPopupPreviewData
import com.shopl.sdg_common.enums.OutlineType
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
        description.takeIf { !it.isNullOrBlank() }?.let {
            Description(
                description = it,
            )
        }

        InputPopupBody(
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
    inputState: InputState,
    onInputChange: (String) -> Unit,
    inputBackgroundColor: Color,
    inputMaxLength: Int,
    enableOnError: Boolean,
    focusRequester: FocusRequester? = null
) {
    SDGText(
        text = inputLabel,
        typography = SDGTypography.Body1R,
        textColor = SDGColor.Neutral400
    )

    Spacer(modifier = Modifier.height(Spacing8))

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
private fun PreviewSDGInputCenterPopup(
    @PreviewParameter(SDGInputCenterPopupParameterProvider::class)
    data: SDGInputCenterPopupPreviewData
) {
    SDGInputCenterPopupPreviewBody(data = data)
}
