package com.shopl.sdg.component.dropdown

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

private const val SDGDropdownDisabledAlpha = 0.3f

enum class SDGDropdownState(
    internal val textColor: Color,
    internal val iconColor: Color,
    internal val isEnabled: Boolean = true,
) {
    Default(
        textColor = SDGColor.Neutral350,
        iconColor = SDGColor.Neutral700,
    ),
    Selected(
        textColor = SDGColor.Neutral700,
        iconColor = SDGColor.Neutral700,
    ),
    Disabled(
        textColor = SDGColor.Neutral700.copy(alpha = SDGDropdownDisabledAlpha),
        iconColor = SDGColor.Neutral300,
        isEnabled = false,
    ),
    Error(
        textColor = SDGColor.Neutral700,
        iconColor = SDGColor.Neutral700,
    ),
    ;

    /** 상태와 Input Field에 맞는 배경색을 반환합니다. */
    internal fun backgroundColor(inputField: SDGDropdownInputField): Color =
        if (this == Error) {
            SDGColor.Red300_a10
        } else {
            inputField.backgroundColor
        }

    /** 상태와 텍스트에 따라 Placeholder 노출 여부를 반환합니다. */
    internal fun isPlaceholderVisible(text: String): Boolean =
        this == Default && text.isEmpty()

    /** 레거시 API의 텍스트에 따라 실제 표시 상태를 반환합니다. */
    internal fun resolveForText(text: String): SDGDropdownState =
        if (this == Default && text.isNotEmpty()) {
            Selected
        } else {
            this
        }
}
