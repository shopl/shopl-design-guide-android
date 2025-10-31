package com.shopl.sdg.template.popup.center.preview

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGDeleteCenterPopupParameterProvider : PreviewParameterProvider<SDGDeleteCenterPopupPreviewData> {
    override val values: Sequence<SDGDeleteCenterPopupPreviewData> = sequenceOf(
        SDGDeleteCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = "팝업 내용입니다.",
            cancelLabel = "취소",
            deleteLabel = "삭제"
        ),
        SDGDeleteCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = "팝업 내용입니다.",
            cancelLabel = "취소",
            deleteLabel = "삭제",
            deleteEnabled = false
        ),
        SDGDeleteCenterPopupPreviewData(
            title = "이것은 화면 중앙에 노출되는 팝업 타이틀입니다. 길어질 경우 2줄까지 노출됩니다.",
            description = "이것은 화면 중앙에 노출되는 팝업 내용입니다. 길어질 경우 스크롤됩니다. ".repeat(10),
            cancelLabel = "취소",
            deleteLabel = "삭제"
        ),
        SDGDeleteCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = null,
            cancelLabel = "취소",
            deleteLabel = "삭제"
        )
    )
}

internal data class SDGDeleteCenterPopupPreviewData(
    val title: String?,
    val description: String?,
    val cancelLabel: String,
    val deleteLabel: String,
    val deleteEnabled: Boolean = true,
    val cancelLabelColor: Color = SDGColor.Neutral600,
    val deleteLabelColor: Color = SDGColor.Red300,
    val titleAlignment: TextAlign = TextAlign.Left,
)