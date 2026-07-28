package com.shopl.sdg.component.select_input.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.select_input.SDGSelectInputField
import com.shopl.sdg.component.select_input.SDGSelectInputImage
import com.shopl.sdg.component.select_input.SDGSelectInputImageElement
import com.shopl.sdg.component.select_input.SDGSelectInputImageType
import com.shopl.sdg.component.select_input.SDGSelectInputState
import com.shopl.sdg.component.select_input.SDGSelectInputText
import com.shopl.sdg.component.select_input.SDGSelectInputType
import com.shopl.sdg_resource.R

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
        text = SDGSelectInputText.Single(
            value = "Selected Text가 길어지면 마지막에 말줄임표가 표시됩니다.",
        ),
        state = SDGSelectInputState.Selected,
    )

    private fun 선택_Text_다중선택() = SDGSelectInputPreviewParameter(
        text = SDGSelectInputText.Multiple(
            value = "Selected Text가 길어지면 마지막에 말줄임표가 표시됩니다.",
            additionalCount = 3,
        ),
        state = SDGSelectInputState.Selected,
    )

    private fun 선택_Avatar() = SDGSelectInputPreviewParameter(
        text = SDGSelectInputText.Single(value = "Selected Text"),
        state = SDGSelectInputState.Selected,
        type = SDGSelectInputPreviewType.Avatar,
    )

    private fun 선택_OneImage_Normal1() = SDGSelectInputPreviewParameter(
        text = SDGSelectInputText.Single(value = "Selected Text"),
        state = SDGSelectInputState.Selected,
        type = SDGSelectInputPreviewType.OneImageNormal1,
    )

    private fun 선택_OneImage_Normal2() = SDGSelectInputPreviewParameter(
        text = SDGSelectInputText.Single(value = "Selected Text"),
        state = SDGSelectInputState.Selected,
        type = SDGSelectInputPreviewType.OneImageNormal2,
    )

    private fun 선택_OneImage_Special1() = SDGSelectInputPreviewParameter(
        text = SDGSelectInputText.Single(value = "Selected Text"),
        state = SDGSelectInputState.Selected,
        type = SDGSelectInputPreviewType.OneImageSpecial1,
    )

    private fun 선택_TwoImage() = SDGSelectInputPreviewParameter(
        text = SDGSelectInputText.Single(value = "First Selected Text"),
        state = SDGSelectInputState.Selected,
        inputField = SDGSelectInputField.White,
        type = SDGSelectInputPreviewType.TwoImage,
    )

    private fun 비활성_Avatar() = SDGSelectInputPreviewParameter(
        text = SDGSelectInputText.Single(value = "Selected Text"),
        state = SDGSelectInputState.Disabled,
        type = SDGSelectInputPreviewType.Avatar,
    )

    private fun 오류_Text() = SDGSelectInputPreviewParameter(
        text = SDGSelectInputText.Single(value = "Selected Text"),
        state = SDGSelectInputState.Error,
    )
}

internal data class SDGSelectInputPreviewParameter(
    val state: SDGSelectInputState,
    val text: SDGSelectInputText = SDGSelectInputText.Single(value = ""),
    val placeholder: String = "Placeholder",
    val inputField: SDGSelectInputField = SDGSelectInputField.LightGray,
    val type: SDGSelectInputPreviewType = SDGSelectInputPreviewType.Text,
)

internal enum class SDGSelectInputPreviewType {
    Text,
    Avatar,
    OneImageNormal1,
    OneImageNormal2,
    OneImageSpecial1,
    TwoImage,
}

/** Preview Parameter를 [SDGSelectInputType]으로 변환합니다. */
internal fun SDGSelectInputPreviewParameter.toSelectInputType(): SDGSelectInputType {
    return when (type) {
        SDGSelectInputPreviewType.Text -> SDGSelectInputType.Text(text = text)
        SDGSelectInputPreviewType.Avatar -> SDGSelectInputType.Avatar(
            text = text,
            selectedElementImage = SDGSelectInputImage.Resource(
                resId = R.drawable.profile_small,
            ),
        )

        SDGSelectInputPreviewType.OneImageNormal1 -> SDGSelectInputType.OneImage(
            text = text,
            image = SDGSelectInputImage.Resource(
                resId = R.drawable.ic_common_photo,
            ),
            type = SDGSelectInputImageType.Normal1,
        )

        SDGSelectInputPreviewType.OneImageNormal2 -> SDGSelectInputType.OneImage(
            text = text,
            image = SDGSelectInputImage.Url(
                url = "https://example.com/image.png",
                failureImageResId = R.drawable.ic_common_photo,
            ),
            type = SDGSelectInputImageType.Normal2,
        )

        SDGSelectInputPreviewType.OneImageSpecial1 -> SDGSelectInputType.OneImage(
            text = text,
            image = SDGSelectInputImage.Resource(
                resId = R.drawable.ic_common_photo,
            ),
            type = SDGSelectInputImageType.Special1,
        )

        SDGSelectInputPreviewType.TwoImage -> SDGSelectInputType.TwoImage(
            first = SDGSelectInputImageElement(
                text = text,
                image = SDGSelectInputImage.Resource(
                    resId = R.drawable.ic_common_photo,
                ),
                state = SDGSelectInputState.Selected,
            ),
            second = SDGSelectInputImageElement(
                text = SDGSelectInputText.Single(
                    value = "Second Selected Text",
                ),
                image = SDGSelectInputImage.Resource(
                    resId = R.drawable.ic_common_photo,
                ),
                state = SDGSelectInputState.Error,
            ),
        )
    }
}
