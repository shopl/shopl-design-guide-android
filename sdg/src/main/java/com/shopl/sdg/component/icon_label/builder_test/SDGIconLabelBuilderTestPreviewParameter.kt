package com.shopl.sdg.component.icon_label.builder_test

import androidx.compose.ui.graphics.Color

internal data class SDGIconLabelBuilderTestPreviewParameter(
    val label: String,
    val labelColor: Color,
    val size: SDGIconLabelBuilderTestSize,
    val fontWeight: SDGIconLabelBuilderTestFontWeight,
    val gap: SDGIconLabelBuilderTestGap,
    val labelOverflow: SDGIconLabelBuilderTestOverflow,
    val leftIcon: SDGIconLabelBuilderTestIcon?,
    val rightIcon: SDGIconLabelBuilderTestIcon?,
)
