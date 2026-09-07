package com.shopl.sdg.template.check_option_label

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.check_option.model.SDGCheckOptionState
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGCheckOptionLabelState(
    internal val checkOptionState: SDGCheckOptionState,
    internal val isEnabled: Boolean = true,
) {
    Default(
        checkOptionState = SDGCheckOptionState.DEFAULT,
    ),
    Selected(
        checkOptionState = SDGCheckOptionState.SELECTED,
    ),
    Disabled(
        checkOptionState = SDGCheckOptionState.DISABLED,
        isEnabled = false,
    ),
    ;

    internal fun labelColor(selectType: SDGCheckOptionLabelSelectedType): Color =
        when (this) {
            Default -> SDGColor.Neutral700
            Selected -> selectType.labelColor
            Disabled -> SDGColor.Neutral300
        }

    /** 레거시 색상 파라미터를 현재 상태에 맞는 라벨 색상으로 변환합니다. */
    internal fun legacyLabelColor(
        defaultTextColor: Color,
        checkTextColor: Color,
    ): Color =
        when (this) {
            Default -> defaultTextColor
            Selected -> checkTextColor
            Disabled -> SDGColor.Neutral300
        }

    companion object {
        /** 레거시 선택 여부와 활성화 여부를 신규 상태로 변환합니다. */
        internal fun fromLegacy(
            isChecked: Boolean,
            enabled: Boolean,
        ): SDGCheckOptionLabelState =
            when {
                !enabled -> Disabled
                isChecked -> Selected
                else -> Default
            }
    }
}
