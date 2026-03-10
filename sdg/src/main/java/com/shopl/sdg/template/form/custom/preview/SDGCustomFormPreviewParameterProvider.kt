package com.shopl.sdg.template.form.custom.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.form.SDGFormType

internal class SDGCustomFormPreviewParameterProvider :
    PreviewParameterProvider<SDGCustomFormPreviewParameter> {

    override val values: Sequence<SDGCustomFormPreviewParameter> = sequenceOf(
        강조형_필수(),
        강조형_필수_아이콘_리프레시(),
        일반형_긴라벨_아이콘(),
    )

    private fun 강조형_필수(): SDGCustomFormPreviewParameter {
        return SDGCustomFormPreviewParameter(
            label = "필수 입력 라벨",
            type = SDGFormType.EMPHA,
            essential = true,
            showIcon = false,
            showRefresh = false,
        )
    }

    private fun 강조형_필수_아이콘_리프레시(): SDGCustomFormPreviewParameter {
        return SDGCustomFormPreviewParameter(
            label = "필수 입력 라벨",
            type = SDGFormType.EMPHA,
            essential = true,
            showIcon = true,
            showRefresh = true,
        )
    }

    private fun 일반형_긴라벨_아이콘(): SDGCustomFormPreviewParameter {
        return SDGCustomFormPreviewParameter(
            label = "Label이 길어지면 줄바꿈으로 전체 노출하는 것이 기본입니다. Label이 길어지면 줄바꿈으로 전체 노출하는 것이 기본입니다.",
            type = SDGFormType.NORMAL,
            essential = false,
            showIcon = true,
            showRefresh = true,
        )
    }
}

internal data class SDGCustomFormPreviewParameter(
    val label: String,
    val type: SDGFormType,
    val essential: Boolean,
    val showIcon: Boolean,
    val showRefresh: Boolean,
)
