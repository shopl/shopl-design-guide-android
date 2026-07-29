package com.shopl.sdg.template.checkbox_label

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

/** Checkbox Label의 상태입니다. */
enum class SDGCheckboxLabelState(
    internal val isChecked: Boolean = false,
    internal val isEnabled: Boolean = true,
) {
    Default,
    Selected(isChecked = true),
    Disabled(isEnabled = false),
    ;

    /** 상태와 선택 타입에 맞는 라벨 색상을 반환합니다. */
    internal fun labelColor(selectType: SDGCheckboxLabelSelectType): Color =
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
        val DEFAULT: SDGCheckboxLabelState = Default

        @Deprecated(
            message = "SELECTED 대신 Selected를 사용하세요.",
            replaceWith = ReplaceWith("Selected"),
        )
        @Suppress("PropertyName")
        val SELECTED: SDGCheckboxLabelState = Selected

        @Deprecated(
            message = "DISABLED 대신 Disabled를 사용하세요.",
            replaceWith = ReplaceWith("Disabled"),
        )
        @Suppress("PropertyName")
        val DISABLED: SDGCheckboxLabelState = Disabled
    }
}
