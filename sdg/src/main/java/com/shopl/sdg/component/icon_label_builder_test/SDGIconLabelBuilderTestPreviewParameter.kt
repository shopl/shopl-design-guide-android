package com.shopl.sdg.component.icon_label_builder_test

import androidx.compose.ui.graphics.Color

internal data class SDGIconLabelBuilderTestPreviewParameter(
    val label: String,
    val labelColor: Color,
    val size: SDGIconLabelBuilderTestSize,
    val fontWeight: SDGIconLabelBuilderTestFontWeight,
    val gap: SDGIconLabelBuilderTestGap,
    val leftIc: SDGIconLabelBuilderTestIcon? = null,
    val rightIc: SDGIconLabelBuilderTestIcon? = null,
    val labelOverflow: SDGIconLabelBuilderTestOverflow = SDGIconLabelBuilderTestOverflow.Full,
    val isFillMaxWidth: Boolean = false,
)
