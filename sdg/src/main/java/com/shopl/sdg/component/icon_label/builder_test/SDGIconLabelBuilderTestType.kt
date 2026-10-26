package com.shopl.sdg.component.icon_label.builder_test

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography

enum class SDGIconLabelBuilderTestSize(
    internal val normalTypography: SDGTypography,
    internal val semiboldTypography: SDGTypography,
    internal val iconVerticalPadding: Dp,
) {
    Medium(SDGTypography.Body1R, SDGTypography.Body1SB, SDGSpacing.Spacing3),
    Small(SDGTypography.Body2R, SDGTypography.Body2SB, SDGSpacing.Spacing2),
    XSmall(SDGTypography.Body3R, SDGTypography.Body3SB, SDGSpacing.Spacing1),
}

enum class SDGIconLabelBuilderTestFontWeight {
    Normal,
    Semibold,
}

enum class SDGIconLabelBuilderTestGap(internal val value: Dp) {
    Spacing2(SDGSpacing.Spacing2),
    Spacing4(SDGSpacing.Spacing4),
}

enum class SDGIconLabelBuilderTestOverflow(
    internal val maxLines: Int,
    internal val textOverflow: TextOverflow,
) {
    Full(Int.MAX_VALUE, TextOverflow.Clip),
    SingleLineEllipsis(1, TextOverflow.Ellipsis),
}

/** Icon Label Builder Test에 표시할 SDG 아이콘과 색상입니다. */
data class SDGIconLabelBuilderTestIcon(
    @DrawableRes val iconResId: Int,
    val iconColor: Color? = null,
)
