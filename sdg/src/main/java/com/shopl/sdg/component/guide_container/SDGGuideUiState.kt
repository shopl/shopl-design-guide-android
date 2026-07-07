package com.shopl.sdg.component.guide_container

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
sealed interface SDGGuideUiState {
    data class True(
        val text: String,
        val textAlignment: SDGGuideTextAlignment,
        val textColor: Color,
    ) : SDGGuideUiState

    data object False : SDGGuideUiState
}
