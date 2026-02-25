package com.shopl.sdg.template.popup.mini_list

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

@Immutable
sealed class SDGMiniListPopupBodyItemText(
    open val textColor: Color,
) {
    abstract val title: String

    data class Default(
        override val title: String,
    ) : SDGMiniListPopupBodyItemText(textColor = SDGColor.Neutral700)

    data class Delete(
        override val title: String,
    ) : SDGMiniListPopupBodyItemText(textColor = SDGColor.Red300)
}