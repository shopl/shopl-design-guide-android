package com.shopl.sdg.component.icon_label_builder_test

import androidx.compose.ui.unit.Dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing1
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing3
import com.shopl.sdg_common.foundation.typography.SDGTypography

enum class SDGIconLabelBuilderTestSize(
    internal val normalTypography: SDGTypography,
    internal val boldTypography: SDGTypography,
    internal val iconVerticalPadding: Dp,
) {
    Medium(
        normalTypography = SDGTypography.Body1R,
        boldTypography = SDGTypography.Body1SB,
        iconVerticalPadding = Spacing3,
    ),
    Small(
        normalTypography = SDGTypography.Body2R,
        boldTypography = SDGTypography.Body2SB,
        iconVerticalPadding = Spacing2,
    ),
    XSmall(
        normalTypography = SDGTypography.Body3R,
        boldTypography = SDGTypography.Body3SB,
        iconVerticalPadding = Spacing1,
    ),
    ;

    companion object {
        @Deprecated(
            message = "Size12 대신 XSmall을 사용하세요.",
            replaceWith = ReplaceWith("XSmall"),
        )
        @Suppress("PropertyName")
        val Size12: SDGIconLabelBuilderTestSize = XSmall

        @Deprecated(
            message = "Size14 대신 Small을 사용하세요.",
            replaceWith = ReplaceWith("Small"),
        )
        @Suppress("PropertyName")
        val Size14: SDGIconLabelBuilderTestSize = Small

        @Deprecated(
            message = "Size16 대신 Medium을 사용하세요.",
            replaceWith = ReplaceWith("Medium"),
        )
        @Suppress("PropertyName")
        val Size16: SDGIconLabelBuilderTestSize = Medium
    }
}
