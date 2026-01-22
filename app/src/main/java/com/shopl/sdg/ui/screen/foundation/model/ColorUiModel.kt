package com.shopl.sdg.ui.screen.foundation.model

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

/**
 * SDG Sample App - Foundation - Color에서 사용되는 Color UI Model
 */
internal data class ColorUiModel(
    val displayLabel: String? = null,
    val color: Color = SDGColor.Transparent
)

internal fun Color.toUiModel(displayLabel: String): ColorUiModel = ColorUiModel(
    displayLabel = displayLabel,
    color = this
)
