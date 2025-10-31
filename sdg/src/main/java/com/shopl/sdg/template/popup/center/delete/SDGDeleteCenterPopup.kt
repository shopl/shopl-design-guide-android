package com.shopl.sdg.template.popup.center.delete

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.template.popup.center.SDGCenterPopup
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.popup.center.preview.SDGDeleteCenterPopupParameterProvider
import com.shopl.sdg.template.popup.center.preview.SDGDeleteCenterPopupPreviewData
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Popup - Center Popup - Delete
 *
 * 삭제 재확인 필요한 [CenterPopup]
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19226-12432&t=dilCeVYD7pIRNnAx-4">Figma</a>
 */
@Composable
fun SDGDeleteCenterPopup(
    title: String?,
    description: String?,
    cancelLabel: String,
    onClickCancel: () -> Unit,
    onClickDelete: () -> Unit,
    deleteLabel: String,
    deleteEnabled: Boolean = true,
    cancelLabelColor: Color = SDGColor.Neutral600,
    deleteLabelColor: Color = SDGColor.Red300,
    titleAlignment: TextAlign = TextAlign.Left,
) {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.DeleteOption(
            deleteLabel = deleteLabel,
            cancelLabel = cancelLabel,
            onClickCancel = onClickCancel,
            onClickDelete = onClickDelete,
            deleteEnabled = deleteEnabled,
            cancelLabelColor = cancelLabelColor,
            deleteLabelColor = deleteLabelColor
        ),
        title = title,
        titleAlignment = titleAlignment,
        body = description.takeIf { !it.isNullOrBlank() }?.let {
            @Composable {
                SDGText(
                    text = it,
                    typography = SDGTypography.Body1R,
                    textColor = SDGColor.Neutral600
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewSDGDeleteCenterPopup(
    @PreviewParameter(SDGDeleteCenterPopupParameterProvider::class)
    data: SDGDeleteCenterPopupPreviewData
) {
    SDGDeleteCenterPopup(
        title = data.title,
        description = data.description,
        cancelLabel = data.cancelLabel,
        onClickCancel = {},
        onClickDelete = {},
        deleteLabel = data.deleteLabel,
        deleteEnabled = data.deleteEnabled,
        cancelLabelColor = data.cancelLabelColor,
        deleteLabelColor = data.deleteLabelColor,
        titleAlignment = data.titleAlignment
    )
}


