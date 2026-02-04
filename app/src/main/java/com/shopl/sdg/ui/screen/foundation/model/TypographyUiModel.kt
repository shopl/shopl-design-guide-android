package com.shopl.sdg.ui.screen.foundation.model

import com.shopl.sdg_common.foundation.typography.SDGTypography

/**
 * SDG Sample App - Foundation - Typography에서 사용되는 UI Model
 */
internal data class TypographyUiModel(
    val styleLabel: String,
    val sizeLabel: String,
    val weightLabel: String,
    val typography: SDGTypography,
)

internal fun SDGTypography.toTypographyUiModel(
    styleLabel: String,
): TypographyUiModel = TypographyUiModel(
    styleLabel = styleLabel,
    sizeLabel = style.fontSize.toString().split(".").first(),
    weightLabel = if (styleLabel.contains("SB")) {
        "Semi Bold"
    } else {
        "Regular"
    },
    typography = this
)
