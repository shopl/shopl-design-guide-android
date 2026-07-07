package com.shopl.sdg.component.guide_container.preview

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.guide_container.SDGGuideUiState

internal class SDGGuideContainerPreviewParameterProvider :
    PreviewParameterProvider<SDGGuideContainerPreviewParams> {

    override val values: Sequence<SDGGuideContainerPreviewParams> = sequenceOf(
        좌측_정렬(),
        우측_정렬(),
    )

    private fun 좌측_정렬() = SDGGuideContainerPreviewParams(
        guide = SDGGuideUiState.Show(
            message = "Guide message",
            messageAlign = TextAlign.Start,
        ),
    )

    private fun 우측_정렬() = SDGGuideContainerPreviewParams(
        guide = SDGGuideUiState.Show(
            message = "Guide message",
            messageAlign = TextAlign.End,
        ),
    )
}
