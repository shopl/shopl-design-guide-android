package com.shopl.sdg.template.popup.mini_list

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

sealed class SDGMiniListPopupItemText(
    open val textColor: Color,
) {
    abstract val title: String

    data class Default(
        override val title: String,
    ) : SDGMiniListPopupItemText(textColor = SDGColor.Neutral700)

    data class Delete(
        override val title: String,
    ) : SDGMiniListPopupItemText(textColor = SDGColor.Red300)
}