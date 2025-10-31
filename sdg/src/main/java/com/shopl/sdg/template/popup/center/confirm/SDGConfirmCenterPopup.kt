package com.shopl.sdg.template.popup.center.confirm

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.template.popup.center.SDGCenterPopup
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.popup.center.preview.SDGConfirmCenterPopupParameterProvider
import com.shopl.sdg.template.popup.center.preview.SDGConfirmCenterPopupPreviewData
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Popup - Center Popup - Confirm
 *
 * 사용자 확인 필요한 [CenterPopup]
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19226-12432&t=dilCeVYD7pIRNnAx-4">Figma</a>
 */
@Composable
fun SDGConfirmCenterPopup(
    title: String?,
    description: String?,
    confirmLabel: String,
    onClickConfirm: () -> Unit,
    cancelLabel: String,
    onClickCancel: () -> Unit,
    onDismiss: (() -> Unit)? = null,
    confirmEnabled: Boolean = true,
    cancelLabelColor: Color = SDGColor.Neutral700,
    confirmLabelColor: Color = SDGColor.Neutral700,
    titleAlignment: TextAlign = TextAlign.Left,
) {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.TwoOption(
            cancelLabel = cancelLabel,
            confirmLabel = confirmLabel,
            onClickCancel = onClickCancel,
            onClickConfirm = onClickConfirm,
            confirmEnabled = confirmEnabled,
            cancelLabelColor = cancelLabelColor,
            confirmLabelColor = confirmLabelColor,
            onDismiss = onDismiss
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
private fun PreviewSDGConfirmCenterPopup(
    @PreviewParameter(SDGConfirmCenterPopupParameterProvider::class)
    data: SDGConfirmCenterPopupPreviewData
) {
    SDGConfirmCenterPopup(
        title = data.title,
        description = data.description,
        confirmLabel = data.confirmLabel,
        onClickConfirm = {},
        cancelLabel = data.cancelLabel,
        onClickCancel = {},
        confirmEnabled = data.confirmEnabled,
        titleAlignment = data.titleAlignment,
        cancelLabelColor = data.cancelLabelColor,
        confirmLabelColor = data.confirmLabelColor
    )
}
