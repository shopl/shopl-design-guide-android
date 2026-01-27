package com.shopl.sdg.ui.screen.foundation.model

import androidx.compose.ui.unit.Dp

/**
 * SDG Sample App - Foundation - Spacing에서 사용되는 Spacing UI Model
 */
internal data class SpacingUiModel(
    val displayLabel: String,
    val size: Dp
)

internal fun Dp.toSpacingUiModel(displayLabel: String): SpacingUiModel = SpacingUiModel(
    displayLabel = displayLabel,
    size = this
)
