package com.shopl.sdg.component.text_input.fixed.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputField
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputState
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputStyle

internal class SDGFixedTextInputPreviewParameterProvider :
    PreviewParameterProvider<SDGFixedTextInputPreviewParameter> {

    override val values: Sequence<SDGFixedTextInputPreviewParameter> = sequenceOf(
        Solid_LightGray_기본(),
        Solid_LightGray_입력완료(),
        Solid_LightGray_오버플로우(),
        Solid_LightGray_비활성(),
        Solid_LightGray_오류(),
        Outlined_White_입력완료(),
        Outlined_White_오류(),
    )

    private fun Solid_LightGray_기본(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Default,
            text = "",
        )
    }

    private fun Solid_LightGray_입력완료(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Completed,
        )
    }

    private fun Solid_LightGray_오버플로우(): SDGFixedTextInputPreviewParameter {
        return SDGFixedTextInputPreviewParameter(
            state = SDGFixedTextInputState.Completed,
            text = """
                가슴속에 못 시인의 나의 별이 봅니다. 어머님, 멀리 별 이런 나는 추억과 남은 걱정도
                쓸쓸함과 있습니다. 다하지 딴은 나의 한 둘 언덕 이름과 별 까닭입니다.
                별을 하나에 소학교 이름과, 봅니다. 하늘에는 별이 계속 이어집니다.
            """.trimIndent(),
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
