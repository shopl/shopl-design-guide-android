package com.shopl.sdg.component.icon_label_builder_test

import androidx.compose.ui.unit.Dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

enum class SDGIconLabelBuilderTestSpacing(val value: Dp) {
    Spacing2(value = SDGSpacing.Spacing2),
    Spacing4(value = SDGSpacing.Spacing4),

    ;

    internal val gap: SDGIconLabelBuilderTestGap
        get() = when (this) {
            Spacing2 -> SDGIconLabelBuilderTestGap.Spacing2
            Spacing4 -> SDGIconLabelBuilderTestGap.Spacing4
        }
}
