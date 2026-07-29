package com.shopl.sdg.component.icon_label

import com.shopl.sdg_common.foundation.typography.SDGTypography

enum class SDGIconLabelType {
    Normal,
    Bold,
    ;

    internal fun typography(size: SDGIconLabelSize): SDGTypography =
        when (this) {
            Normal -> size.normalTypography
            Bold -> size.boldTypography
        }

    companion object {
        @Deprecated(
            message = "Basic 대신 Normal을 사용하세요.",
            replaceWith = ReplaceWith("Normal"),
        )
        @Suppress("PropertyName")
        val Basic: SDGIconLabelType = Normal

        @Deprecated(
            message = "Empha 대신 Bold를 사용하세요.",
            replaceWith = ReplaceWith("Bold"),
        )
        @Suppress("PropertyName")
        val Empha: SDGIconLabelType = Bold
    }
}
