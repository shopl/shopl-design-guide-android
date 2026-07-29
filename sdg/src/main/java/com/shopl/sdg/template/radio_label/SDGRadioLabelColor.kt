package com.shopl.sdg.template.radio_label

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

/**
 * 신규 Radio Label API와의 하위 호환성을 위한 레거시 라벨 색상 타입입니다.
 */
@Deprecated(
    message = "SDGRadioLabelSelectType을 사용하세요.",
)
enum class SDGRadioLabelColor(val color: Color) {
    BASIC(color = SDGColor.Neutral700),
    COLOR(color = SDGColor.Primary300),
}
