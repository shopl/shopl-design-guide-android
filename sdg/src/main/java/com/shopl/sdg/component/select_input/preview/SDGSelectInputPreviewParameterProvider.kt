package com.shopl.sdg.component.select_input.preview

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.select_input.SDGSelectInputField
import com.shopl.sdg.component.select_input.SDGSelectInputState

internal class SDGSelectInputPreviewParameterProvider :
    PreviewParameterProvider<SDGSelectInputPreviewParameter> {

    override val values: Sequence<SDGSelectInputPreviewParameter> = sequenceOf(
        기본_LightGray(),
        선택_Text_단일선택(),
        선택_Text_다중선택(),
        선택_Avatar(),
        선택_OneImage_Normal1(),
        선택_OneImage_Normal2(),
        선택_OneImage_Special1(),
        선택_TwoImage(),
        비활성_Avatar(),
        오류_Text(),
    )

    private fun 기본_LightGray() = SDGSelectInputPreviewParameter(
        state = SDGSelectInputState.Default,
    )

    private fun 선택_Text_단일선택() = SDGSelectInputPreviewParameter(
        text = "Selected Text가 길어지면 마지막에 말줄임표가 표시됩니다.",
        state = SDGSelectInputState.Selected,
    )

    private fun 선택_Text_다중선택() = SDGSelectInputPreviewParameter(
        text = "Selected Text가 길어지면+3",
        state = SDGSelectInputState.Selected,
        overflow = TextOverflow.MiddleEllipsis,
    )

    private fun 선택_Avatar() = SDGSelectInputPreviewParameter(
        text = "Selected Text",
        state = SDGSelectInputState.Selected,
        type = SDGSelectInputPreviewType.Avatar,
    )

    private fun 선택_OneImage_Normal1() = SDGSelectInputPreviewParameter(
        text = "Selected Text",
        state = SDGSelectInputState.Selected,
        type = SDGSelectInputPreviewType.OneImageNormal1,
    )

    private fun 선택_OneImage_Normal2() = SDGSelectInputPreviewParameter(
        text = "Selected Text",
        state = SDGSelectInputState.Selected,
        type = SDGSelectInputPreviewType.OneImageNormal2,
    )

    private fun 선택_OneImage_Special1() = SDGSelectInputPreviewParameter(
        text = "Selected Text",
        state = SDGSelectInputState.Selected,
        type = SDGSelectInputPreviewType.OneImageSpecial1,
    )

    private fun 선택_TwoImage() = SDGSelectInputPreviewParameter(
        text = "First Selected Text",
        state = SDGSelectInputState.Selected,
        inputField = SDGSelectInputField.White,
        type = SDGSelectInputPreviewType.TwoImage,
    )

    private fun 비활성_Avatar() = SDGSelectInputPreviewParameter(
        text = "Selected Text",
        state = SDGSelectInputState.Disabled,
        type = SDGSelectInputPreviewType.Avatar,
    )

    private fun 오류_Text() = SDGSelectInputPreviewParameter(
        text = "Selected Text",
        state = SDGSelectInputState.Error,
    )
}

internal data class SDGSelectInputPreviewParameter(
    val state: SDGSelectInputState,
    val text: String? = null,
    val placeholder: String = "Placeholder",
    val inputField: SDGSelectInputField = SDGSelectInputField.LightGray,
    val type: SDGSelectInputPreviewType = SDGSelectInputPreviewType.Text,
    val overflow: TextOverflow = TextOverflow.Ellipsis,
)

internal enum class SDGSelectInputPreviewType {
    Text,
    Avatar,
    OneImageNormal1,
    OneImageNormal2,
    OneImageSpecial1,
    TwoImage,
}
