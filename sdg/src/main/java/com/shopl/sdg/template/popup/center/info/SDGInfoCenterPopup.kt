package com.shopl.sdg.template.popup.center.info

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.template.popup.center.SDGCenterPopup
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.popup.center.preview.SDGInfoCenterPopupPreviewData
import com.shopl.sdg.template.popup.center.preview.SDGInfoCenterPopupPreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Popup - Center Popup - Info
 *
 * 단순 정보 전달하는 [CenterPopup]
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19226-12432&t=dilCeVYD7pIRNnAx-4">Figma</a>
 */
@Composable
fun SDGInfoCenterPopup(
    title: String?,
    description: String?,
    confirmLabel: String,
    onClickConfirm: () -> Unit,
    confirmLabelColor: Color = SDGColor.Neutral700,
    titleAlignment: TextAlign = TextAlign.Left,
    enabled: Boolean = true,
) {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.OneOption(
            label = confirmLabel,
            onClick = onClickConfirm,
            labelColor = confirmLabelColor,
            enabled = enabled
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
private fun PreviewSDGInfoCenterPopup(
    @PreviewParameter(SDGInfoCenterPopupPreviewParameterProvider::class)
    data: SDGInfoCenterPopupPreviewData
) {
    SDGInfoCenterPopup(
        title = data.title,
        description = data.description,
        confirmLabel = data.confirmLabel,
        onClickConfirm = {},
        confirmLabelColor = data.confirmLabelColor,
        titleAlignment = data.titleAlignment,
        enabled = data.enabled
    )
}