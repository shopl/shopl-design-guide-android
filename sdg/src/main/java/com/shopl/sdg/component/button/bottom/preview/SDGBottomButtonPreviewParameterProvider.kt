package com.shopl.sdg.component.button.bottom.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSize
import com.shopl.sdg.component.button.bottom.SDGBottomButtonStyle

internal class SDGBottomButtonPreviewParameterProvider :
    PreviewParameterProvider<SDGBottomButtonPreviewParameter> {

    override val values: Sequence<SDGBottomButtonPreviewParameter> = sequenceOf(
        기본_Full_Primary(),
        비활성_Full_Primary(),
        기본_Full_Neutral(),
        비활성_Full_Neutral(),
        기본_Full_Point(),
        비활성_Full_Point(),
        기본_Full_Normal(),
        비활성_Full_Normal(),
        기본_Adaptive_Primary(),
        비활성_Adaptive_Primary(),
        기본_Adaptive_Neutral(),
        비활성_Adaptive_Neutral(),
        기본_Adaptive_Point(),
        비활성_Adaptive_Point(),
        기본_Adaptive_Normal(),
        비활성_Adaptive_Normal(),
        긴_라벨_Full(),
        긴_라벨_Adaptive(),
    )

    private fun 기본_Full_Primary() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Primary,
        size = SDGBottomButtonSize.Full,
        enabled = true,
    )

    private fun 비활성_Full_Primary() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Primary,
        size = SDGBottomButtonSize.Full,
        enabled = false,
    )

    private fun 기본_Full_Neutral() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Neutral,
        size = SDGBottomButtonSize.Full,
        enabled = true,
    )

    private fun 비활성_Full_Neutral() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Neutral,
        size = SDGBottomButtonSize.Full,
        enabled = false,
    )

    private fun 기본_Full_Point() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Point,
        size = SDGBottomButtonSize.Full,
        enabled = true,
    )

    private fun 비활성_Full_Point() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Point,
        size = SDGBottomButtonSize.Full,
        enabled = false,
    )

    private fun 기본_Full_Normal() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Normal,
        size = SDGBottomButtonSize.Full,
        enabled = true,
    )

    private fun 비활성_Full_Normal() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Normal,
        size = SDGBottomButtonSize.Full,
        enabled = false,
    )

    private fun 기본_Adaptive_Primary() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Primary,
        size = SDGBottomButtonSize.Adaptive,
        enabled = true,
    )

    private fun 비활성_Adaptive_Primary() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Primary,
        size = SDGBottomButtonSize.Adaptive,
        enabled = false,
    )

    private fun 기본_Adaptive_Neutral() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Neutral,
        size = SDGBottomButtonSize.Adaptive,
        enabled = true,
    )

    private fun 비활성_Adaptive_Neutral() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Neutral,
        size = SDGBottomButtonSize.Adaptive,
        enabled = false,
    )

    private fun 기본_Adaptive_Point() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Point,
        size = SDGBottomButtonSize.Adaptive,
        enabled = true,
    )

    private fun 비활성_Adaptive_Point() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Point,
        size = SDGBottomButtonSize.Adaptive,
        enabled = false,
    )

    private fun 기본_Adaptive_Normal() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Normal,
        size = SDGBottomButtonSize.Adaptive,
        enabled = true,
    )

    private fun 비활성_Adaptive_Normal() = SDGBottomButtonPreviewParameter(
        label = "Label",
        style = SDGBottomButtonStyle.Normal,
        size = SDGBottomButtonSize.Adaptive,
        enabled = false,
    )

    private fun 긴_라벨_Full() = SDGBottomButtonPreviewParameter(
        label = "한 줄로 표시할 수 있는 너비를 넘은 긴 버튼명은 말줄임으로 표시합니다",
        style = SDGBottomButtonStyle.Primary,
        size = SDGBottomButtonSize.Full,
        enabled = true,
    )

    private fun 긴_라벨_Adaptive() = SDGBottomButtonPreviewParameter(
        label = "한 줄로 표시할 수 있는 너비를 넘은 긴 버튼명은 말줄임으로 표시합니다",
        style = SDGBottomButtonStyle.Neutral,
        size = SDGBottomButtonSize.Adaptive,
        enabled = true,
    )
}
