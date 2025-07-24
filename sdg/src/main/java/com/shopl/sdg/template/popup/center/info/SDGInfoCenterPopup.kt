package com.shopl.sdg.template.popup.center.info

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.template.popup.center.SDGCenterPopup
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
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
    title: String,
    description: String,
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
    ) {
        SDGText(
            text = description,
            typography = SDGTypography.Body1R,
            textColor = SDGColor.Neutral600
        )
    }
}

@Preview
@Composable
fun PreviewSDGInfoCenterPopup() {
    SDGInfoCenterPopup(
        title = "Title",
        description = "Description",
        confirmLabel = "확인",
        onClickConfirm = {},
        confirmLabelColor = SDGColor.Neutral700,
        titleAlignment = TextAlign.Left,
        enabled = true,
    )
}




