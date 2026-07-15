package com.shopl.sdg.template.popup.bottom

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

@Stable
sealed interface SDGBottomPopupButtonOption {
    data class OneOption(
        val label: String,
        val onClick: () -> Unit,
        val labelColor: Color = SDGColor.Neutral700,
    ) : SDGBottomPopupButtonOption

    data class TwoOption(
        val startLabel: String,
        val endLabel: String,
        val onClickStart: () -> Unit,
        val onClickEnd: () -> Unit,
        val startLabelColor: Color = SDGColor.Neutral700,
        val endLabelColor: Color = SDGColor.Neutral700,
    ) : SDGBottomPopupButtonOption
}
