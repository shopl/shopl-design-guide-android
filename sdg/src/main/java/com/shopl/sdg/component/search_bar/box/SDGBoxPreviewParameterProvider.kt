package com.shopl.sdg.component.search_bar.box

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SDGBoxPreviewParameterProvider : PreviewParameterProvider<SDGBoxPreviewParameter> {
    override val values: Sequence<SDGBoxPreviewParameter> = sequenceOf(
        Solid_기본(),
        Solid_입력중(),
        Solid_비활성(),
        Solid_입력중_비활성(),
        Solid_NEUTRAL50(),
        Line_기본()
    )

    private fun Solid_기본() = SDGBoxPreviewParameter(
        type = SDGBoxSearchType.Solid(),
        input = ""
    )

    private fun Solid_입력중() = SDGBoxPreviewParameter(
        type = SDGBoxSearchType.Solid(),
        input = "입력중"
    )

    private fun Solid_비활성() = SDGBoxPreviewParameter(
        type = SDGBoxSearchType.Solid(),
        input = "",
        enabled = false
    )

    private fun Solid_입력중_비활성() = SDGBoxPreviewParameter(
        type = SDGBoxSearchType.Solid(),
        input = "입력중 비활성 됨",
        enabled = false
    )

    private fun Solid_NEUTRAL50() = SDGBoxPreviewParameter(
        type = SDGBoxSearchType.Solid(
            backgroundColor = SDGBoxSearchType.Solid.SolidColor.Neutral50,
        ),
        input = ""
    )

    private fun Line_기본() = SDGBoxPreviewParameter(
        type = SDGBoxSearchType.Line,
        input = ""
    )
}

internal data class SDGBoxPreviewParameter(
    val type: SDGBoxSearchType,
    val input: String,
    val enabled: Boolean = true,
)
