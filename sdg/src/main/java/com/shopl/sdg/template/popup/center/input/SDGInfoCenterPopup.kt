package com.shopl.sdg.template.popup.center.input

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
    confirmOnClick: () -> Unit,
    confirmLabelColor: Color = SDGColor.Neutral700,
    titleAlignment: TextAlign = TextAlign.Left,
    enabled: Boolean = true,
) {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.OneOption(
            label = confirmLabel,
            onClick = confirmOnClick,
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