package com.shopl.sdg.template.popup.center.preview

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGInfoCenterPopupPreviewParameterProvider : PreviewParameterProvider<SDGInfoCenterPopupPreviewData> {
    override val values: Sequence<SDGInfoCenterPopupPreviewData> = sequenceOf(
        SDGInfoCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = "팝업 내용입니다.",
            confirmLabel = "확인"
        ),
        SDGInfoCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = "팝업 내용입니다.",
            confirmLabel = "확인",
            enabled = false
        ),
        SDGInfoCenterPopupPreviewData(
            title = "이것은 화면 중앙에 노출되는 팝업 타이틀입니다. 길어질 경우 2줄까지 노출됩니다.",
            description = "이것은 화면 중앙에 노출되는 팝업 내용입니다. 길어질 경우 스크롤됩니다. ".repeat(10),
            confirmLabel = "확인"
        ),
        SDGInfoCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = null,
            confirmLabel = "확인"
        )
    )
}

internal data class SDGInfoCenterPopupPreviewData(
    val title: String?,
    val description: String?,
    val confirmLabel: String,
    val confirmLabelColor: Color = SDGColor.Neutral700,
    val titleAlignment: TextAlign = TextAlign.Left,
    val enabled: Boolean = true,
)