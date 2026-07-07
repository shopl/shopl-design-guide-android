package com.shopl.sdg.component.guide_container.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.guide_container.SDGGuideTextAlignment
import com.shopl.sdg.component.guide_container.ShowText
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGGuideContainerPreviewParameterProvider :
    PreviewParameterProvider<SDGGuideContainerPreviewParams> {

    override val values: Sequence<SDGGuideContainerPreviewParams> = sequenceOf(
        텍스트_노출_좌측_정렬(),
        텍스트_노출_우측_정렬(),
        텍스트_비노출(),
    )

    private fun 텍스트_노출_좌측_정렬() = SDGGuideContainerPreviewParams(
        showText = ShowText.True(
            text = "Guide message",
            textAlignment = SDGGuideTextAlignment.LEFT,
            textColor = SDGColor.Neutral700,
        ),
    )

    private fun 텍스트_노출_우측_정렬() = SDGGuideContainerPreviewParams(
        showText = ShowText.True(
            text = "Guide message",
            textAlignment = SDGGuideTextAlignment.RIGHT,
            textColor = SDGColor.Neutral700,
        ),
    )

    private fun 텍스트_비노출() = SDGGuideContainerPreviewParams(
        showText = ShowText.False,
    )
}
