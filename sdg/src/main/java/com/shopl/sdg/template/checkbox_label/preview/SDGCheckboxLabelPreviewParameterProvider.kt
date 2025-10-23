package com.shopl.sdg.template.checkbox_label.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabelType
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGCheckboxLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGCheckboxLabelPreviewParams> {

    override val values = sequenceOf(
        // Default - Normal
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.NORMAL,
            label = "Label",
            isChecked = false,
        ),
        // Default - Empha
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.EMPHA,
            label = "Label",
            isChecked = false,
        ),

        // Selected - Normal - Neutral700
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.NORMAL,
            label = "Label",
            isChecked = true,
        ),
        // Selected - Empha - Neutral700
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.EMPHA,
            label = "Label",
            isChecked = true,
        ),
        // Selected - Normal - Primary300
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.NORMAL,
            label = "Label",
            checkTextColor = SDGColor.Primary300,
            isChecked = true,
        ),
        // Selected - Empha - Neutral700
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.EMPHA,
            label = "Label",
            checkTextColor = SDGColor.Primary300,
            isChecked = true,
        ),

        // Disabled - Normal
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.NORMAL,
            label = "Label",
            isChecked = false,
            enabled = false
        ),
        // Disabled - Empha
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.EMPHA,
            label = "Label",
            isChecked = true,
            enabled = false
        ),
    )
}
