package com.shopl.sdg.template.checkbox_label

import com.shopl.sdg_common.foundation.typography.SDGTypography

/** 신규 Checkbox Label API와의 하위 호환성을 위한 레거시 타입입니다. */
@Deprecated(
    message = "SDGCheckboxLabelState와 SDGCheckboxLabelSelectType을 사용하세요.",
)
enum class SDGCheckboxLabelType(val typography: SDGTypography) {
    NORMAL(typography = SDGTypography.Body1R),
    EMPHA(typography = SDGTypography.Body1SB),
}
