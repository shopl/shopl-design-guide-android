package com.shopl.sdg.component.avatar

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SDGAvatarPreviewParameterProvider : PreviewParameterProvider<SDGAvatarPreviewParameter> {
    override val values = sequenceOf(
        SDGAvatarSize.XL,
        SDGAvatarSize.L,
        SDGAvatarSize.M,
        SDGAvatarSize.S,
        SDGAvatarSize.XS,
        SDGAvatarSize.XXS,
    ).flatMap { size ->
        sequenceOf(
            기본_이미지(size),
            테두리만_표시(size),
            아이콘만_표시(size),
            재시도만_표시(size),
            재시도_테두리_아이콘_표시(size),
        )
    }

    private fun 기본_이미지(size: SDGAvatarSize) = SDGAvatarPreviewParameter(
        avatarSize = size,
        showFace = false,
        showOutline = false,
        showIc = false,
        showRetry = false,
    )

    private fun 테두리만_표시(size: SDGAvatarSize) = 기본_이미지(size).copy(showOutline = true)

    private fun 아이콘만_표시(size: SDGAvatarSize) = 기본_이미지(size).copy(showIc = true)

    private fun 재시도만_표시(size: SDGAvatarSize) = 기본_이미지(size).copy(showRetry = true)

    private fun 재시도_테두리_아이콘_표시(size: SDGAvatarSize) = 기본_이미지(size).copy(
        showFace = true,
        showOutline = true,
        showIc = true,
        showRetry = true,
    )
}
