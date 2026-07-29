package com.shopl.sdg.template.radio_label

/**
 * 신규 Radio Label API와의 하위 호환성을 위한 레거시 상태 타입입니다.
 */
@Deprecated(
    message = "SDGRadioLabelState를 사용하세요.",
)
enum class SDGRadioLabelStatus {
    DEFAULT,
    SELECTED,
    DISABLED,
    ;

    internal fun toState(): SDGRadioLabelState =
        when (this) {
            DEFAULT -> SDGRadioLabelState.Default
            SELECTED -> SDGRadioLabelState.Selected
            DISABLED -> SDGRadioLabelState.Disabled
        }
}
