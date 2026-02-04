package com.shopl.sdg.ui.screen.foundation.model

import androidx.compose.ui.unit.Dp

/**
 * SDG Sample App - Foundation - Iconography에서 사용되는 UI Model
 */
internal data class IconographyUiModel(
    val displayLabel: String,
    val size: Dp
)

internal fun Dp.toIconographyUiModel(displayLabel: String): IconographyUiModel = IconographyUiModel(
    displayLabel = displayLabel,
    size = this
)
