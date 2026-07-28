package com.shopl.sdg.component.select_input

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

@Stable
sealed class SDGSelectInputField(
    val color: Color,
    val fieldName: String,
) {
    data object LightGray : SDGSelectInputField(
        color = SDGColor.Neutral50,
        fieldName = "LightGray",
    )

    data object White : SDGSelectInputField(
        color = SDGColor.Neutral0,
        fieldName = "White",
    )
}
