package com.shopl.sdg.component.text_input.simple.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputField
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputState
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputStyle

private const val TEXT_WITH_OVERFLOW = "가슴속에 못 시인의 나의 별이 봅니다. 어머님, 멀리 계신 하늘의 별을 바라봅니다."

internal class SDGSimpleTextInputPreviewParameterProvider :
    PreviewParameterProvider<SDGSimpleTextInputPreviewParameter> {
    override val values: Sequence<SDGSimpleTextInputPreviewParameter> = sequenceOf(
        SDGSimpleTextInputPreviewParameter(
            state = SDGSimpleTextInputState.Default,
            text = "",
        ),
        SDGSimpleTextInputPreviewParameter(
            state = SDGSimpleTextInputState.Completed,
        ),
        SDGSimpleTextInputPreviewParameter(
            state = SDGSimpleTextInputState.Completed,
            text = TEXT_WITH_OVERFLOW,
        ),
        SDGSimpleTextInputPreviewParameter(
            state = SDGSimpleTextInputState.Disabled,
        ),
        SDGSimpleTextInputPreviewParameter(
            state = SDGSimpleTextInputState.Error,
        ),
        SDGSimpleTextInputPreviewParameter(
            state = SDGSimpleTextInputState.Error,
            text = TEXT_WITH_OVERFLOW,
        ),
        SDGSimpleTextInputPreviewParameter(
            state = SDGSimpleTextInputState.Completed,
            inputField = SDGSimpleTextInputField.White,
            style = SDGSimpleTextInputStyle.Outlined,
        ),
        SDGSimpleTextInputPreviewParameter(
            state = SDGSimpleTextInputState.Error,
            inputField = SDGSimpleTextInputField.White,
            style = SDGSimpleTextInputStyle.Outlined,
        ),
    )
}

internal data class SDGSimpleTextInputPreviewParameter(
    val state: SDGSimpleTextInputState,
    val inputField: SDGSimpleTextInputField = SDGSimpleTextInputField.LightGray,
    val style: SDGSimpleTextInputStyle = SDGSimpleTextInputStyle.Solid,
    val text: String = "Text",
    val placeholder: String = "Placeholder",
)
