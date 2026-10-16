package com.shopl.sdg.component.progress.linear.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGLinearProgressPreviewParameterProvider :
    PreviewParameterProvider<SDGLinearProgressPreviewParameter> {

    override val values: Sequence<SDGLinearProgressPreviewParameter> = sequenceOf(
        SDGLinearProgressPreviewParameter(),
        SDGLinearProgressPreviewParameter(progress = 0f),
        SDGLinearProgressPreviewParameter(progress = 0.5f),
        SDGLinearProgressPreviewParameter(progress = 1f, label = "업로드가 완료됐습니다"),
        SDGLinearProgressPreviewParameter(label = null),
        SDGLinearProgressPreviewParameter(progress = 0.5f, label = null),
        SDGLinearProgressPreviewParameter(label = "파일을 업로드하고 있습니다. 잠시만 기다려 주세요."),
        SDGLinearProgressPreviewParameter(labelColor = SDGColor.Primary400),
    )
}
