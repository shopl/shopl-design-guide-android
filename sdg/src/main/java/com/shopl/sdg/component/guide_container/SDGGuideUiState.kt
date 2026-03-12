package com.shopl.sdg.component.guide_container

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

@Stable
sealed interface SDGGuideUiState {
    data class Show(
        val message: String,
        val messageColor: Color = SDGColor.Neutral700,
    ) : SDGGuideUiState

    data object Hide : SDGGuideUiState
}
