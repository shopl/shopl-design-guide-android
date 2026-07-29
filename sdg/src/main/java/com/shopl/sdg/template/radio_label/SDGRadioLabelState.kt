package com.shopl.sdg.template.radio_label

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.radio.SDGRadioStatus
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGRadioLabelState(
    internal val radioStatus: SDGRadioStatus,
    internal val isEnabled: Boolean = true,
) {
    Default(
        radioStatus = SDGRadioStatus.DEFAULT,
    ),
    Selected(
        radioStatus = SDGRadioStatus.SELECTED,
    ),
    Disabled(
        radioStatus = SDGRadioStatus.DISABLED,
        isEnabled = false,
    ),
    ;

    internal val isSelected: Boolean
        get() = this == Selected

    /** 상태와 선택 타입에 맞는 라벨 색상을 반환합니다. */
    internal fun labelColor(selectType: SDGRadioLabelSelectType): Color =
        when (this) {
            Default -> SDGColor.Neutral700
            Selected -> selectType.labelColor
            Disabled -> SDGColor.Neutral300
        }

    companion object {
        @Deprecated(
            message = "DEFAULT 대신 Default를 사용하세요.",
            replaceWith = ReplaceWith("Default"),
        )
        @Suppress("PropertyName")
        val DEFAULT: SDGRadioLabelState = Default

        @Deprecated(
            message = "SELECTED 대신 Selected를 사용하세요.",
            replaceWith = ReplaceWith("Selected"),
        )
        @Suppress("PropertyName")
        val SELECTED: SDGRadioLabelState = Selected

        @Deprecated(
            message = "DISABLED 대신 Disabled를 사용하세요.",
            replaceWith = ReplaceWith("Disabled"),
        )
        @Suppress("PropertyName")
        val DISABLED: SDGRadioLabelState = Disabled
    }
}
