package com.shopl.sdg.component.time_select_input

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

private const val SDGTimeSelectInputDisabledAlpha = 0.3f

enum class SDGTimeSelectInputState(
    internal val textColor: Color,
    internal val contentAlpha: Float = 1f,
    internal val isEnabled: Boolean = true,
) {
    Default(
        textColor = SDGColor.Neutral350,
    ),
    Selected(
        textColor = SDGColor.Neutral700,
    ),
    Disabled(
        textColor = SDGColor.Neutral700,
        contentAlpha = SDGTimeSelectInputDisabledAlpha,
        isEnabled = false,
    ),
    Error(
        textColor = SDGColor.Neutral700,
    ),
    ;

    /** 상태와 Input Field에 맞는 배경색을 반환합니다. */
    internal fun backgroundColor(inputField: SDGTimeSelectInputField): Color =
        if (this == Error) {
            SDGColor.Red300_a10
        } else {
            inputField.backgroundColor
        }

    /** 상태와 텍스트에 따라 Placeholder 노출 여부를 반환합니다. */
    internal fun isPlaceholderVisible(text: String?): Boolean =
        this == Default || text.isNullOrEmpty()

    /** 레거시 API의 시간 값에 따라 실제 표시 상태를 반환합니다. */
    internal fun resolveForTime(
        startTime: String?,
        endTime: String?,
    ): SDGTimeSelectInputState =
        if (this == Default && (!startTime.isNullOrEmpty() || !endTime.isNullOrEmpty())) {
            Selected
        } else {
            this
        }
}
