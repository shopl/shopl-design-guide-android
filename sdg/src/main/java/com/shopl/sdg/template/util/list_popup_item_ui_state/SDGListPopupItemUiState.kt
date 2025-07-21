package com.shopl.sdg.template.util.list_popup_item_ui_state

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

@Stable
sealed class SDGListPopupItemUiState(
    open val title: String,
    val textColor: Color,
) {
    companion object {
        fun create(title: String, shouldSelect: Boolean): SDGListPopupItemUiState {
            return if (shouldSelect) {
                Selected(title)
            } else {
                Default(title)
            }
        }
    }

    data class Default(override val title: String) :
        SDGListPopupItemUiState(title, SDGColor.Neutral700)

    data class Selected(override val title: String) :
        SDGListPopupItemUiState(title, SDGColor.Primary300)

    data class Delete(override val title: String) : SDGListPopupItemUiState(title, SDGColor.Red300)

}