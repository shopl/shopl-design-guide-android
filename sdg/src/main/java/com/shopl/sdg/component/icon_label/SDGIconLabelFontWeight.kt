package com.shopl.sdg.component.icon_label

import com.shopl.sdg_common.foundation.typography.SDGTypography

enum class SDGIconLabelFontWeight {
    Normal,
    Bold,
    ;

    internal fun typography(size: SDGIconLabelSize): SDGTypography =
        when (this) {
            Normal -> size.normalTypography
            Bold -> size.boldTypography
        }
}
