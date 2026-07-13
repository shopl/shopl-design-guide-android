package com.shopl.sdg.component.time_select_input.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.time_select_input.SDGTimeSelectInputField
import com.shopl.sdg.component.time_select_input.SDGTimeSelectInputState

internal class SDGTimeSelectInputPreviewParameterProvider :
    PreviewParameterProvider<SDGTimeSelectInputPreviewParams> {

    override val values = sequenceOf(
        기본_상태(),
        선택_상태(),
        부분_선택_상태(),
        흰색_필드_상태(),
        비활성_상태(),
        오류_상태(),
    )

    private fun 기본_상태() = SDGTimeSelectInputPreviewParams(
        state = SDGTimeSelectInputState.Default,
        inputField = SDGTimeSelectInputField.LIGHT_GRAY,
        placeholder = "Placeholder",
        startTime = null,
        endTime = null,
    )

    private fun 선택_상태() = SDGTimeSelectInputPreviewParams(
        state = SDGTimeSelectInputState.Selected,
        inputField = SDGTimeSelectInputField.LIGHT_GRAY,
        placeholder = "Placeholder",
        startTime = "09:00",
        endTime = "18:00",
    )

    private fun 부분_선택_상태() = SDGTimeSelectInputPreviewParams(
        state = SDGTimeSelectInputState.Selected,
        inputField = SDGTimeSelectInputField.LIGHT_GRAY,
        placeholder = "Placeholder",
        startTime = "09:00",
        endTime = null,
    )

    private fun 흰색_필드_상태() = SDGTimeSelectInputPreviewParams(
        state = SDGTimeSelectInputState.Selected,
        inputField = SDGTimeSelectInputField.WHITE,
        placeholder = "Placeholder",
        startTime = "09:00",
        endTime = "18:00",
    )

    private fun 비활성_상태() = SDGTimeSelectInputPreviewParams(
        state = SDGTimeSelectInputState.Disabled,
        inputField = SDGTimeSelectInputField.LIGHT_GRAY,
        placeholder = "Placeholder",
        startTime = "09:00",
        endTime = "18:00",
    )

    private fun 오류_상태() = SDGTimeSelectInputPreviewParams(
        state = SDGTimeSelectInputState.Error,
        inputField = SDGTimeSelectInputField.WHITE,
        placeholder = "Placeholder",
        startTime = "09:00",
        endTime = "18:00",
    )
}
