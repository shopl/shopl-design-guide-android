package com.shopl.sdg.component.dropdown.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.dropdown.SDGDropdownInputField
import com.shopl.sdg.component.dropdown.SDGDropdownState

internal class SDGDropdownPreviewParameterProvider :
    PreviewParameterProvider<SDGDropdownPreviewParams> {

    override val values: Sequence<SDGDropdownPreviewParams> = sequenceOf(
        기본_상태_WHITE(),
        기본_상태_LIGHT_GRAY(),
        선택_상태_WHITE(),
        선택_상태_LIGHT_GRAY(),
        선택_상태_말줄임표(),
        비활성_상태_LIGHT_GRAY(),
        오류_상태(),
        오류_상태_말줄임표(),
    )

    private fun 기본_상태_WHITE() = SDGDropdownPreviewParams(
        text = "",
        placeholder = "Placeholder",
        state = SDGDropdownState.Default,
        inputField = SDGDropdownInputField.White,
    )

    private fun 기본_상태_LIGHT_GRAY() = SDGDropdownPreviewParams(
        text = "",
        placeholder = "Placeholder",
        state = SDGDropdownState.Default,
        inputField = SDGDropdownInputField.LightGray,
    )

    private fun 선택_상태_WHITE() = SDGDropdownPreviewParams(
        text = "Selected Text",
        placeholder = "Placeholder",
        state = SDGDropdownState.Selected,
        inputField = SDGDropdownInputField.White,
    )

    private fun 선택_상태_LIGHT_GRAY() = SDGDropdownPreviewParams(
        text = "Selected Text",
        placeholder = "Placeholder",
        state = SDGDropdownState.Selected,
        inputField = SDGDropdownInputField.LightGray,
    )

    private fun 선택_상태_말줄임표() = SDGDropdownPreviewParams(
        text = "Selected Text가 Input Field 영역 이상으로 길어진 경우",
        placeholder = "Placeholder",
        state = SDGDropdownState.Selected,
        inputField = SDGDropdownInputField.White,
    )

    private fun 비활성_상태_LIGHT_GRAY() = SDGDropdownPreviewParams(
        text = "Selected Text",
        placeholder = "Placeholder",
        state = SDGDropdownState.Disabled,
        inputField = SDGDropdownInputField.LightGray,
    )

    private fun 오류_상태() = SDGDropdownPreviewParams(
        text = "Selected Text",
        placeholder = "Placeholder",
        state = SDGDropdownState.Error,
        inputField = SDGDropdownInputField.White,
    )

    private fun 오류_상태_말줄임표() = SDGDropdownPreviewParams(
        text = "Selected Text가 Input Field 영역 이상으로 길어진 경우",
        placeholder = "Placeholder",
        state = SDGDropdownState.Error,
        inputField = SDGDropdownInputField.White,
    )
}
