package com.shopl.sdg.component.icon_label

import androidx.compose.ui.graphics.Color

internal data class SDGIconLabelPreviewParameter(
    val label: String,
    val labelColor: Color,
    val size: SDGIconLabelSize,
    val fontWeight: SDGIconLabelFontWeight,
    val gap: SDGIconLabelGap,
    val leftIc: SDGIconLabelIcon? = null,
    val rightIc: SDGIconLabelIcon? = null,
    val labelOverflow: SDGIconLabelOverflow = SDGIconLabelOverflow.Full,
    val isFillMaxWidth: Boolean = false,
)
