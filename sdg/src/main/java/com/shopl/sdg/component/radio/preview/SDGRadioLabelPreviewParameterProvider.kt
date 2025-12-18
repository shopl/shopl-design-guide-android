package com.shopl.sdg.component.radio.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.radio.SDGRadioLabelType
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGRadioLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGRadioLabelPreviewParams> {

    override val values = sequenceOf(
        // Label Top Alignment
        SDGRadioLabelPreviewParams(
            type = SDGRadioLabelType.NORMAL,
            label = "Label\nLabel",
            isChecked = false,
        ),

        // Default - Normal
        SDGRadioLabelPreviewParams(
            type = SDGRadioLabelType.NORMAL,
            label = "Label",
            isChecked = false,
        ),
        // Default - Empha
        SDGRadioLabelPreviewParams(
            type = SDGRadioLabelType.EMPHA,
            label = "Label",
            isChecked = false,
        ),

        // Selected - Normal - Neutral700
        SDGRadioLabelPreviewParams(
            type = SDGRadioLabelType.NORMAL,
            label = "Label",
            isChecked = true,
        ),
        // Selected - Empha - Neutral700
        SDGRadioLabelPreviewParams(
            type = SDGRadioLabelType.EMPHA,
            label = "Label",
            isChecked = true,
        ),
        // Selected - Normal - Primary300
        SDGRadioLabelPreviewParams(
            type = SDGRadioLabelType.NORMAL,
            label = "Label",
            checkTextColor = SDGColor.Primary300,
            isChecked = true,
        ),
        // Selected - Empha - Neutral700
        SDGRadioLabelPreviewParams(
            type = SDGRadioLabelType.EMPHA,
            label = "Label",
            checkTextColor = SDGColor.Primary300,
            isChecked = true,
        ),

        // Disabled - Normal
        SDGRadioLabelPreviewParams(
            type = SDGRadioLabelType.NORMAL,
            label = "Label",
            isChecked = false,
            enabled = false
        ),
        // Disabled - Empha
        SDGRadioLabelPreviewParams(
            type = SDGRadioLabelType.EMPHA,
            label = "Label",
            isChecked = true,
            enabled = false
        ),
    )
}
