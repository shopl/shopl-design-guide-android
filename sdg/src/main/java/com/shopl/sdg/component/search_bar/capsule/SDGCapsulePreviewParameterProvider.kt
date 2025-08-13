package com.shopl.sdg.component.search_bar.capsule

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SDGCapsulePreviewParameterProvider : PreviewParameterProvider<SDGCapsulePreviewParameter> {
    override val values: Sequence<SDGCapsulePreviewParameter> = sequenceOf(
        SDGCapsulePreviewParameter(SDGCapsuleSearchType.Solid, "입력중", true),
        SDGCapsulePreviewParameter(SDGCapsuleSearchType.Solid, "", false),
        SDGCapsulePreviewParameter(SDGCapsuleSearchType.Line, "", true),
        SDGCapsulePreviewParameter(SDGCapsuleSearchType.Line, "Disabled", false),
    )
}

internal data class SDGCapsulePreviewParameter(
    val type: SDGCapsuleSearchType,
    val input: String,
    val enabled: Boolean
)