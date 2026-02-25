package com.shopl.sdg.component.search_bar.box

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SDGBoxPreviewParameterProvider : PreviewParameterProvider<SDGBoxPreviewParameter> {
    override val values: Sequence<SDGBoxPreviewParameter> = sequenceOf(
        SDGBoxPreviewParameter(SDGBoxSearchType.Solid(), "입력중", true),
        SDGBoxPreviewParameter(
            SDGBoxSearchType.Solid(
                backgroundColor = SDGBoxSearchType.Solid.SolidColor.Neutral50,
            ),
            "",
            true,
        ),
        SDGBoxPreviewParameter(SDGBoxSearchType.Line, "", true),
        SDGBoxPreviewParameter(SDGBoxSearchType.Line, "Disabled", false),
    )
}

internal data class SDGBoxPreviewParameter(
    val type: SDGBoxSearchType,
    val input: String,
    val enabled: Boolean,
)
