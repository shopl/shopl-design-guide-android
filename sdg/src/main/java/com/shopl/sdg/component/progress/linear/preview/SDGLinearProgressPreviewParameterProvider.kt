package com.shopl.sdg.component.progress.linear.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.progress.linear.SDGLinearProgressType.Determinate
import com.shopl.sdg.component.progress.linear.SDGLinearProgressType.Indeterminate
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGLinearProgressPreviewParameterProvider :
    PreviewParameterProvider<SDGLinearProgressPreviewParameter> {

    override val values: Sequence<SDGLinearProgressPreviewParameter> = sequenceOf(
        SDGLinearProgressPreviewParameter(type = Indeterminate),
        진행률_0퍼센트(),
        진행률_50퍼센트(),
        진행률_100퍼센트(),
        SDGLinearProgressPreviewParameter(type = Indeterminate, label = null),
        라벨_없는_진행률(),
        SDGLinearProgressPreviewParameter(type = Indeterminate, label = "파일을 업로드하고 있습니다. 잠시만 기다려 주세요."),
        SDGLinearProgressPreviewParameter(type = Indeterminate, labelColor = SDGColor.Primary400),
    )

    private fun 진행률_0퍼센트() = SDGLinearProgressPreviewParameter(
        type = Determinate(0f),
    )

    private fun 진행률_50퍼센트() = SDGLinearProgressPreviewParameter(
        type = Determinate(0.5f),
    )

    private fun 진행률_100퍼센트() = SDGLinearProgressPreviewParameter(
        type = Determinate(1f),
        label = "업로드가 완료됐습니다",
    )

    private fun 라벨_없는_진행률() = SDGLinearProgressPreviewParameter(
        type = Determinate(0.5f),
        label = null,
    )
}
