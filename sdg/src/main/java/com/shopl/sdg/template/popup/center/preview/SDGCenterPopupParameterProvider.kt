package com.shopl.sdg.template.popup.center.preview

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

internal class SDGCenterPopupParameterProvider : PreviewParameterProvider<SDGCenterPopupPreviewData> {
    override val values: Sequence<SDGCenterPopupPreviewData> = sequenceOf(
        SDGCenterPopupPreviewData(
            buttonOption = SDGCenterPopupButtonOption.OneOption(
                label = "확인",
                onClick = {},
                labelColor = SDGColor.Neutral700
            ),
            title = "팝업 타이틀",
            body = {
                SDGText(
                    text = "팝업 내용입니다. ".repeat(20),
                    typography = SDGTypography.Body1R,
                    textColor = SDGColor.Neutral700
                )
            }
        ),
        SDGCenterPopupPreviewData(
            buttonOption = SDGCenterPopupButtonOption.OneOption(
                label = "확인",
                onClick = {},
                labelColor = SDGColor.Neutral700
            ),
            title = "팝업 타이틀",
            body = null
        ),
        SDGCenterPopupPreviewData(
            buttonOption = SDGCenterPopupButtonOption.TwoOption(
                cancelLabel = "취소",
                confirmLabel = "확인",
                onClickCancel = {},
                onClickConfirm = {},
                cancelLabelColor = SDGColor.Neutral700,
                confirmLabelColor = SDGColor.Neutral700
            ),
            title = "팝업 타이틀",
            titleAlignment = TextAlign.Center,
            body = {
                SDGText(
                    text = "팝업 내용입니다. ".repeat(20),
                    typography = SDGTypography.Body1R,
                    textColor = SDGColor.Neutral700
                )
            }
        ),
        SDGCenterPopupPreviewData(
            buttonOption = SDGCenterPopupButtonOption.DeleteOption(
                deleteLabel = "삭제",
                cancelLabel = "취소",
                onClickCancel = {},
                onClickDelete = {},
                deleteLabelColor = SDGColor.Red300,
                cancelLabelColor = SDGColor.Neutral700
            ),
            title = "팝업 타이틀",
            body = {
                repeat(40) {
                    SDGText(
                        text = "팝업 내용입니다. ",
                        typography = SDGTypography.Body1R,
                        textColor = SDGColor.Neutral700
                    )
                }
            }
        )
    )
}

internal data class SDGCenterPopupPreviewData(
    val buttonOption: SDGCenterPopupButtonOption,
    val title: String? = null,
    val titleAlignment: TextAlign = TextAlign.Left,
    val body: @Composable (ColumnScope.() -> Unit)?
)