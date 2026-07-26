package com.shopl.sdg.component.text_input.fixed.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputField
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputState
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputStyle

internal class SDGFixedTextInputPreviewParameterProvider :
    PreviewParameterProvider<SDGFixedTextInputPreviewParameter> {

    override val values: Sequence<SDGFixedTextInputPreviewParameter> = sequenceOf(
        Solid_LightGray_기본(),
        Solid_LightGray_포커스(),
        Solid_LightGray_입력완료(),
        Solid_LightGray_비활성(),
        Solid_LightGray_오류(),
        Outlined_White_입력완료(),
        Solid_White_포커스(),
        Outlined_White_오류(),
    )

    private fun Solid_LightGray_기본(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Default,
            text = "",
        )
    }

    private fun Solid_LightGray_포커스(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Focused,
        )
    }

    private fun Solid_LightGray_입력완료(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Completed,
        )
    }

    private fun Solid_LightGray_비활성(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Disabled,
        )
    }

    private fun Solid_LightGray_오류(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Error,
        )
    }

    private fun Outlined_White_입력완료(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Completed,
            inputField = SDGFixedTextInputField.White,
            style = SDGFixedTextInputStyle.Outlined,
        )
    }

    private fun Solid_White_포커스(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Focused,
            inputField = SDGFixedTextInputField.White,
        )
    }

    private fun Outlined_White_오류(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Error,
            inputField = SDGFixedTextInputField.White,
            style = SDGFixedTextInputStyle.Outlined,
        )
    }
}

internal data class SDGFixedTextInputPreviewParameter(
    val state: SDGFixedTextInputState,
    val inputField: SDGFixedTextInputField = SDGFixedTextInputField.LightGray,
    val style: SDGFixedTextInputStyle = SDGFixedTextInputStyle.Solid,
    val text: String = "Text",
    val placeholder: String = "Placeholder",
)
