package com.shopl.sdg.component.icon_label_builder_test

import com.shopl.sdg_common.foundation.typography.SDGTypography

enum class SDGIconLabelBuilderTestFontWeight {
    Normal,
    Bold,
    ;

    internal fun typography(size: SDGIconLabelBuilderTestSize): SDGTypography =
        when (this) {
            Normal -> size.normalTypography
            Bold -> size.boldTypography
        }
}
