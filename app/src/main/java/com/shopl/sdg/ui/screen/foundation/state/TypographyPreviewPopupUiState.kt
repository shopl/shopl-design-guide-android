package com.shopl.sdg.ui.screen.foundation.state

import androidx.compose.runtime.Stable
import com.shopl.sdg.ui.screen.foundation.model.TypographyUiModel
import kotlinx.collections.immutable.ImmutableList

@Stable
internal sealed interface TypographyPreviewPopupUiState {
    data object Hidden : TypographyPreviewPopupUiState
    data class Visible(
        val uiModels: ImmutableList<TypographyUiModel>
    ) : TypographyPreviewPopupUiState
}
