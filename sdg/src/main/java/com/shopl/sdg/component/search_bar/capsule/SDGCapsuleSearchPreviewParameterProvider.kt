package com.shopl.sdg.component.search_bar.capsule

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SDGCapsuleSearchPreviewParameterProvider :
    PreviewParameterProvider<SDGCapsuleSearchPreviewParameter> {
    override val values: Sequence<SDGCapsuleSearchPreviewParameter> = sequenceOf(
        Solid_White(),
        Solid_White_disable(),
        Line_White(),
        Solid_Light_Gray(),
        Solid_Light_Gray_disable(),
        Line_Light_Gray()
    )

    private fun Solid_White() = SDGCapsuleSearchPreviewParameter(
        type = SDGCapsuleSearchType.Solid,
        input = "Solid/White",
        enabled = true,
        backgroundColor = SDGCapsuleSearchBackgroundColor.WHITE
    )

    private fun Solid_White_disable() = SDGCapsuleSearchPreviewParameter(
        type = SDGCapsuleSearchType.Solid,
        input = "Solid/White/disable",
        enabled = false,
        backgroundColor = SDGCapsuleSearchBackgroundColor.WHITE
    )

    private fun Line_White() = SDGCapsuleSearchPreviewParameter(
        type = SDGCapsuleSearchType.Line,
        input = "Line/White",
        enabled = true,
        backgroundColor = SDGCapsuleSearchBackgroundColor.WHITE
    )

    private fun Solid_Light_Gray() = SDGCapsuleSearchPreviewParameter(
        type = SDGCapsuleSearchType.Solid,
        input = "Solid/LightGray",
        enabled = true,
        backgroundColor = SDGCapsuleSearchBackgroundColor.LIGHT_GRAY
    )

    private fun Solid_Light_Gray_disable() = SDGCapsuleSearchPreviewParameter(
        type = SDGCapsuleSearchType.Solid,
        input = "Solid/LightGray/disable",
        enabled = false,
        backgroundColor = SDGCapsuleSearchBackgroundColor.LIGHT_GRAY
    )

    private fun Line_Light_Gray() = SDGCapsuleSearchPreviewParameter(
        type = SDGCapsuleSearchType.Line,
        input = "Line/LightGray",
        enabled = true,
        backgroundColor = SDGCapsuleSearchBackgroundColor.LIGHT_GRAY
    )
}

internal data class SDGCapsuleSearchPreviewParameter(
    val type: SDGCapsuleSearchType,
    val input: String,
    val enabled: Boolean,
    val backgroundColor: SDGCapsuleSearchBackgroundColor,
)